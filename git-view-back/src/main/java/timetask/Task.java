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
import service.AreaLanguageService;
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
    AreaLanguageService areaLanguageService;

    @Autowired
    RequestUtil requestUtil;

    @Autowired
    ThreadPool threadPool;

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

    @Scheduled(cron = "0 15 10 16 * ?")
    public void getLocationCount() throws IOException{
        getCountriesData();
        getChineseData();
        getAmericanData();
    }

    @Scheduled(cron = "0 0 19 15 * ?")
    public void getAreaLanguageCount(){
        getCountriesAreaData();
        getAmericanAreaData();
        getChineseAreaData();
    }


    private void getLanguagesData(String method){
        for(Language language: Language.values()){
            final Language lan = language;
            final String met = method;
            threadPool.excute(new Runnable(){
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
                threadPool.excute(new Runnable(){
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
            threadPool.excute(new Runnable(){
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
        threadPool.excute(new Runnable(){
            public void run(){
                try{
                    requestLimit.consume();
                    locationDetailService.updateUsers(loc.getName(), cou, Search.LocationUserCount(loc));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void getCountriesAreaData(){
        for(WorldArea worldArea: WorldArea.values()){
            for(Language language: Language.values()){
                getAreaLanguageData(worldArea, language);
            }
        }
    }

    private void getChineseAreaData(){
        for(ChineseArea chineseArea: ChineseArea.values()){
            for(Language language: Language.values()){
                getAreaLanguageData(chineseArea, language);
            }
        }
    }

    private void getAmericanAreaData(){
        for(AmericanArea americanArea: AmericanArea.values()){
            for(Language language: Language.values()){
                getAreaLanguageData(americanArea, language);
            }
        }
    }

    private void getAreaLanguageData(Area location, Language language){
        final Area loc = location;
        final Language lan = language;
        threadPool.excute(new Runnable(){
            public void run(){
                try{
                    requestLimit.consume();
                    areaLanguageService.updateUsers(lan.getValue(), loc.getName(), Search.LanguageAndLocationUserCount(lan, loc));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}