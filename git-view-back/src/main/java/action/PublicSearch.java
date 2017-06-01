package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import po.AreaDetail;
import po.AreaLanguage;
import po.Language;
import po.LanguageDetail;
import po.WorldArea;
import po.YearDetail;
import service.AreaLanguageService;
import service.LanguageDetailService;
import service.LocationDetailService;
import service.YearDetailService;
import util.RequestUtil;
import util.Search;

@Controller
@CrossOrigin
@RequestMapping("/search")
public class PublicSearch{
    @Autowired
    RequestUtil requestUtil;

    @Autowired
    LanguageDetailService languageDetailService;

    @Autowired
    YearDetailService yearDetailService;

    @Autowired
    LocationDetailService locationDetailService;

    @Autowired
    AreaLanguageService areaLanguageService;

    @ResponseBody
    @RequestMapping("/support_languages")
    public List<LanguageDetail> languageDetail() throws IOException{
        return languageDetailService.findAll();
    }

    @ResponseBody
    @RequestMapping("/year")
    public List<YearDetail> languageYear(String language, Integer year) {
        if(language!=null&&year==null) return yearDetailService.findByLanguage(language);
        if(year!=null&&language==null) return yearDetailService.findByYear(year);
        if(year!=null&&language!=null) {
            List<YearDetail> list = new ArrayList<YearDetail>();
            list.add(yearDetailService.findByLanguageAndYear(language, year));
            return list;
        }
        return yearDetailService.findAll();
    }

    @ResponseBody
    @RequestMapping("/language")
    public List<String> languageRepos(String language, int page, int per_page) throws IOException{
        return Search.LanguageRepos(language, page, per_page);
    }

    @ResponseBody
    @RequestMapping("/languagelist")
    public List<String> languageList(){
        List<String> list = new ArrayList<String>();
        for(Language language: Language.values()){
            list.add(language.getValue());
        }
        return list;
    }

    @ResponseBody
    @RequestMapping("/country")
    public List<AreaDetail> countryUserCount(String country){
        if(country == null) return locationDetailService.findCountries();
        return locationDetailService.findByCountry(country);
    }

    @ResponseBody
    @RequestMapping("/support_areas")
    public Map<String,List<String>> allAreas(){
        Map<String,List<String>> map = new HashMap<String,List<String>>();
        for(WorldArea country: WorldArea.values()){
            List<AreaDetail> areas = locationDetailService.findByCountry(country.getName());
            List<String> list = new ArrayList<String>();
            for(AreaDetail area: areas){
                if(!area.getLocation().equals(country.getName())) list.add(area.getLocation());
            }
            map.put(country.getName(), list);
        }
        return map;
    }

    @ResponseBody
    @RequestMapping("/location")
    public AreaDetail locationUserCount(String location){
        return locationDetailService.findByLocation(location);
    }

    @ResponseBody
    @RequestMapping("/area_language")
    public List<AreaLanguage> areaLanguageCount(String location, String language){
        if(language!=null&&location==null) return areaLanguageService.findByLanguage(language);
        if(location!=null&&language==null) return areaLanguageService.findByArea(location);
        if(location!=null&&language!=null) {
            List<AreaLanguage> list = new ArrayList<AreaLanguage>();
            list.add(areaLanguageService.findByAreaAndLanguage(location, language));
            return list;
        }
        return areaLanguageService.findAll();
    }
}