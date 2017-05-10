package timetask;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import po.AmericanArea;
import po.Area;
import po.ChineseArea;
import po.Language;
import po.WorldArea;
import po.YearDetail;
import service.LanguageDetailService;
import service.LocationDetailService;
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
    LocationDetailService locationDetailService;

    @Autowired
    RequestUtil requestUtil;

    public Task(){}

    @Scheduled(fixedRate = 1000 * 60 * 2, initialDelay = 1000 * 60 * 2)
    public void refreshLimit(){
        RefreshThread t = new RefreshThread(requestLimit);
        t.start();
    }

    @Scheduled(cron = "0 0 12 * * ?")
    public void getLanguage() throws IOException{
            getLanguagesData("repo");
            getLanguagesData("user");
    }

    @Scheduled(cron = "0 0 13 * * ?")
    public void getYearCount() throws IOException{
            // getYearData("repo");
            // getYearData("push");
            getRecentYearData("repo");
            getRecentYearData("push");
    }

    @Scheduled(cron = "0 0 10 * * ?")
    public void getLocationCount() throws IOException{
        getCountriesData();
        getChineseData();
        getAmericanData();
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
    
    @SuppressWarnings("all")
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

    private void getCountriesData(){
        for(WorldArea worldArea: WorldArea.values()){
            getLocationData(worldArea, worldArea);
        }
    }

    private void getChineseData(){
        for(ChineseArea chineseArea: ChineseArea.values()){
            getLocationData(chineseArea, WorldArea.China);
        }
    }

    private void getAmericanData(){
        for(AmericanArea americanArea: AmericanArea.values()){
            getLocationData(americanArea, WorldArea.USA);
        }
    }

    private void getLocationData(Area location, WorldArea country){
        final Area loc = location;
        final String cou = country.getName();
        Thread thread = new Thread(new Runnable(){
            public void run(){
                try{
                    requestLimit.consume();
                    locationDetailService.updateUsers(loc.getName(), cou, Search.LocationUserCount(loc));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}