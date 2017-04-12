package action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.RequestUtil;

@Controller
@RequestMapping("/search")
public class PublicSearch{
    @Autowired
    RequestUtil requestUtil;

    @SuppressWarnings("unused")
    private void setRequestUtil(RequestUtil requestUtil){
        this.requestUtil = requestUtil;
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
        if(key == null) key = ".*";
        return requestUtil.requestForMap(
            "https://api.github.com/search/commits?q="+key+"+repo:mybatis/spring",
            "GET", null, "application/vnd.github.cloak-preview");
    }
}