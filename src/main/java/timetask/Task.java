package timetask;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import po.Language;
import po.Proxy;
import po.YearDetail;
import service.LanguageDetailService;
import service.YearDetailService;
import util.RequestUtil;
import util.Search;

@Component
public class Task{
    @Autowired
    RequestLimit requestLimit;

    @Autowired
    LanguageDetailService languageDetailService;

    @Autowired
    YearDetailService yearDetailService;

    @Autowired
    ProxyPool proxyPool;

    @Autowired
    RequestUtil requestUtil;

    public Task(){}

    @Scheduled(fixedRate = 1000 * 60 * 2, initialDelay = 1000 * 60 * 2)
    public void refreshLimit(){
        RefreshThread t = new RefreshThread(requestLimit);
        t.start();
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24, initialDelay = 0)
    public void getYearCount() throws IOException{
            getLanguagesData("repo");
            getLanguagesData("user");
            getYearData("repo");
            getYearData("push");
            getRecentYearData("repo");
            getRecentYearData("push");
    }

    @Scheduled(fixedRate = 1000 * 60 * 1, initialDelay = 0)
    public void offerProxy() throws Exception{
        String str = requestUtil.request("http://www.89ip.cn/api/?&tqsl=50&sxa=&sxb=&tta=&ports=&ktip=&cf=1");
        String[] temp = str.split("\r\n");
        String[] ips = temp[3].split("<br>")[0].split("<BR>");
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50, 100, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50));
        for(String ip: ips){
            String[] pp = ip.split(":");
            System.out.println(ip);
            final Proxy proxy = new Proxy(pp[0], Integer.parseInt(pp[1]));
            threadPool.execute(new Runnable(){
                public void run(){
                    if(proxyPool.checkProxy(proxy)) proxyPool.offerProxy(proxy);
                }
            });
        }   
    }

    @Scheduled(fixedRate = 1000 * 60 * 1, initialDelay = 1000 * 60 * 1)
    public void ProxyFilter(){
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50, 100, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50));
        Iterator<Proxy> iterator = proxyPool.iterator();
        while(iterator.hasNext()){
            final Proxy proxy = iterator.next();
            threadPool.execute(new Runnable(){
                public void run(){
                    if(!proxyPool.checkProxy(proxy)) proxyPool.deleteProxy(proxy);
                }
            });
        }
    }

    private void getLanguagesData(String method){
        for(Language language: Language.values()){
            final Language lan = language;
            final String met = method;
            Thread thread = new Thread(new Runnable(){
                public void run(){
                    try{
                        requestLimit.consume();
                        if(met.equals("repo")) languageDetailService.updateRepo(lan.getValue(), Search.LanguageCount(lan));
                        if(met.equals("user")) languageDetailService.updateUsers(lan.getValue(), Search.LanguageUserCount(lan));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            thread.start();   
        }
    }
    
    @SuppressWarnings("deprecation")
    private void getYearData(String method){
        for(Language language: Language.values()){
            final Language lan = language;
            final String met = method;
            Date date = new Date();
            for(int year=2008;year<date.getYear()+1900;year++){
                final int y = year;
                YearDetail detail = yearDetailService.findByLanguageAndYear(language.getValue(), year);
                if(detail!=null){
                    if((detail.getRepo()!=-1 && method.equals("repo")) || (detail.getPush()!=-1 && method.equals("push"))){
                        continue;
                    }
                }
                Thread thread = new Thread(new Runnable(){
                    public void run(){
                        try{
                            requestLimit.consume();
                            if(met.equals("repo")) yearDetailService.updateRepo(lan.getValue(), y, Search.LanguageYearCount(lan, y));
                            if(met.equals("push")) yearDetailService.updatePush(lan.getValue(), y, Search.LanguageYearPushCount(lan, y));
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                thread.start();   
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void getRecentYearData(String method){
        for(Language language: Language.values()){
            final Language lan = language;
            final String met = method;
            Date date = new Date();
            final int y = date.getYear()+1900;
            Thread thread = new Thread(new Runnable(){
                public void run(){
                    try{
                        requestLimit.consume();
                        if(met.equals("repo")) yearDetailService.updateRepo(lan.getValue(), y, Search.LanguageYearCount(lan, y));
                        if(met.equals("push")) yearDetailService.updatePush(lan.getValue(), y, Search.LanguageYearPushCount(lan, y));
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        }
    }
}