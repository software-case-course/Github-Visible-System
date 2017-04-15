package action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import util.RequestUtil;

@Controller
@RequestMapping("/private")
public class PrivateSearch{
    @Autowired
    RequestUtil requestUtil;

    String access_token = null;

    @SuppressWarnings("unused")
    private void setRequestUtil(RequestUtil requestUtil){
        this.requestUtil = requestUtil;
    }

    @RequestMapping("/getcode")
    public void getcode(HttpServletResponse response) throws IOException{
        response.sendRedirect("https:/github.com/login/oauth/authorize?client_id=2687357014dac98c13a9&scope=user");
    }

    @RequestMapping("/gettoken")
    public void gettoken(HttpServletRequest request, HttpServletResponse response) throws IOException{
        access_token = requestUtil.requestForMap("https://github.com/login/oauth/access_token",
         "POST",
         "client_id=2687357014dac98c13a9&client_secret=e6808532ec8db2b38dacf1a5514589200476e242&code="+request.getParameter("code"),
         null);
    }
}