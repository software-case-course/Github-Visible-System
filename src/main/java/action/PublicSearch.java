package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import po.Language;
import po.LanguageDetail;
import po.YearDetail;
import service.LanguageDetailService;
import service.YearDetailService;
import util.RequestUtil;
import util.Search;
import util.Search2;

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
    Search2 search2;

    /**
     * @param repo
     * @param sort stars|forks|updated
     * @param order asc|desc
     * @param begin get commits after the date, format: 2016-01-01
     * @param end get commits before the date, format: 2016-01-01
     * @return json of repositories
     */
    
    // @ResponseBody
    // @RequestMapping("/commit")
    // public String searchCommit(String key) throws Exception{
    //     if(key == null) key = "";
    //     return requestUtil.requestForMap(
    //         "https://api.github.com/search/commits?q="+key+"+repo:mybatis/spring",
    //         "GET", null, "application/vnd.github.cloak-preview",null);
    // }

    @ResponseBody
    @RequestMapping("/repos")
    public List<Map<String,String>> searchRepos(String q){
        JSONObject json = search2.searchReposByKey(q);
        return search2.selectRepos(json);
    }

    @ResponseBody
    @RequestMapping("/lanrepos")
    public List<Map<String,String>> searchReposForLanguage(String language){
        JSONObject json = search2.searchReposByLanguage(language);
        return search2.selectRepos(json);
    }

    @ResponseBody
    @RequestMapping("/languages")
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
}