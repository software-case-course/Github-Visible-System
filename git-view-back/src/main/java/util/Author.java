package util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Author{
    @Autowired
    RequestUtil requestUtil;

    public String user(String token) {
        String result = null;
        try{
            result = requestUtil.request("https://api.github.com/user?access_token=" + token);
        }catch (Exception e){
            // System.err.println(e.getLocalizedMessage());
            return "{\"error_message\": 401}";
        }
        return result;
    }

    public String repos(String token) {
        String result = null;
        try{
            result = requestUtil.request("https://api.github.com/user/repos?access_token=" + token);
        }catch (Exception e){
            // System.err.println(e.getLocalizedMessage());
            return "{\"error_message\": 401}";
        }
        return result;
    }
}