package timetask;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

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

    boolean isFirst = true;

    public Task(){}

    @Scheduled(fixedRate = 1000 * 60 * 2, initialDelay = 1000 * 60 * 2)
    public void refreshLimit(){
        RefreshThread t = new RefreshThread(requestLimit);
        t.start();
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void getLanguageCount(){
        getLanguagesData("repo");
        getLanguagesData("user");
    }

    @Scheduled(fixedRate = 1000 * 60 * 60 * 24, initialDelay = 0)
    public void getYearCount() throws IOException{
        if(isFirst){
            getLanguagesData("repo");
            getLanguagesData("user");
            getYearData("repo");
            getYearData("push");           
            isFirst = false;
        }
        else{
            getRecentYearData("repo");
            getRecentYearData("push");
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 10, initialDelay = 0)
    public void offerProxy(){
        if(!proxyPool.overload()){
            try{
                String result = requestUtil.request("http://www.xdaili.cn/ipagent//freeip/getFreeIps");
                JSONObject json = requestUtil.StringToJson(result);
                JSONArray rows = json.getJSONArray("rows");
                Iterator<Object> iterator = rows.iterator();
                while(iterator.hasNext()){
                    JSONObject row = (JSONObject)iterator.next();
                    System.out.println(row.getString("ip")+":"+row.getInt("port"));
                    proxyPool.offerProxy(new Proxy(row.getString("ip"), row.getInt("port")));
                }
            }catch(Exception e){
                System.err.println(e);
            }
        }
        else{
            proxyPool.clearProxy(10);
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
            for(int year=2008;year<=date.getYear()+1900;year++){
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