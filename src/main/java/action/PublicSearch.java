package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonStructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;

import po.LanguageDetail;
import po.YearDetail;
import service.LanguageDetailService;
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

    @SuppressWarnings("unused")
    private void setRequestUtil(RequestUtil requestUtil){
        this.requestUtil = requestUtil;
    }

    @SuppressWarnings("unused")
    private void setLanguageDetailService(LanguageDetailService languageDetailService){
        this.languageDetailService = languageDetailService;
    }

    @SuppressWarnings("unused")
    private void setYearDetailService(YearDetailService yearDetailService){
        this.yearDetailService = yearDetailService;
    }

    /**
     * @param key
     * @param language e.g. java
     * @param sort stars|forks|updated
     * @param order asc|desc
     * @return json of repositories
     */
    @ResponseBody
    @RequestMapping("/repo")
    public String searchRepo(String key, String language, String sort, String order){
        if(key == null) key = "";
        return requestUtil.requestForMap(
            "https://api.github.com/search/repositories?q="+key+"+language:"+language+"&sort="+sort+"&order="+order,
            "GET", null, null);
    }

    /**
     * @param repo
     * @param sort stars|forks|updated
     * @param order asc|desc
     * @param begin get commits after the date, format: 2016-01-01
     * @param end get commits before the date, format: 2016-01-01
     * @return json of repositories
     */
    
    @ResponseBody
    @RequestMapping("/commit")
    public String searchCommit(String key) throws Exception{
        if(key == null) key = "";
        return requestUtil.requestForMap(
            "https://api.github.com/search/commits?q="+key+"+repo:mybatis/spring",
            "GET", null, "application/vnd.github.cloak-preview");
    }

    @ResponseBody
    @RequestMapping("/repos")
    public JsonStructure searchRepos() throws IOException{
        Github github = new RtGithub();
        JsonStructure json = github.entry().uri().path("/search/repositories")
            .queryParam("q", "node.js")
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().read();
        return json;
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
}