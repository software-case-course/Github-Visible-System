package action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import util.Author;
import util.Authorize;
import util.Relative;
import util.RequestUtil;

@Controller
@CrossOrigin
@RequestMapping("/private")
public class PrivateSearch{
    @Autowired
    RequestUtil requestUtil;

    @Autowired
    Author author;

    String access_token = null;

    @SuppressWarnings("unused")
    private void setRequestUtil(RequestUtil requestUtil){
        this.requestUtil = requestUtil;
    }

    @RequestMapping("/authorize")
    public void authorize(String redirect_uri,HttpServletResponse response) throws IOException{
        response.sendRedirect("https:/github.com/login/oauth/authorize?client_id=2687357014dac98c13a9&scope=user&redirect_uri="+redirect_uri);
    }

    @ResponseBody
    @RequestMapping("/gettoken")
    public String gettoken(HttpServletRequest request, HttpServletResponse response){
        try{
            String token = Authorize.getToken(request.getParameter("code"));
            return token;
        }catch(IOException e){
            e.printStackTrace();
            return "get token fail";
        }
    }

    @ResponseBody
    @RequestMapping("/user")
    public String getUser(String token){
        return author.user(token);
    }

    @ResponseBody
    @RequestMapping("/repos")
    public String getRepos(String token){
        return author.repos(token);
    }

    @ResponseBody
    @RequestMapping("/languages")
    public Map<String,Integer> getLanguages(String token){
        Map<String,Integer> map = null;
        try{
            map = Relative.codes(token);
        }catch(IOException e){}
        return map;
    }
}