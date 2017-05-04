package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import po.Proxy;
import timetask.ProxyPool;

@Repository
public class Search2{
    @Autowired
    ProxyPool proxyPool;

    @Autowired
    RequestUtil requestUtil;

    public Search2(){}

    public JSONObject searchReposByKey(String q){
        return searchRepos("https://api.github.com/search/repositories?q=" + q + "&sort=stars&order=desc");
    }

    public JSONObject searchReposByLanguage(String language){
        return searchRepos("https://api.github.com/search/repositories?q=language:" + language + "&sort=stars&order=desc");
    }

    public JSONObject searchRepos(String url){
        String result = null;
        JSONObject json;
        boolean access = false;
        while(!access){
            try{
                Proxy proxy = proxyPool.useProxy();
                result = requestUtil.request(url,"GET", null, null, requestUtil.getProxy(proxy.getIp(), proxy.getPort()));
                access = true;
                System.out.println("connected success");
            }catch(Exception e){
                proxyPool.popProxy();
                System.out.println("connected fail, try again");
            }
        }
        json = new JSONObject(result);
        return json;
    }

    public List<Map<String,String>> selectRepos(JSONObject json){
        List<Map<String,String>> array = new ArrayList<Map<String,String>>();
        JSONArray items = json.getJSONArray("items");
        Iterator<Object> iterator = items.iterator();
        while(iterator.hasNext()){
            JSONObject item = (JSONObject)iterator.next();
            Map<String,String> map = new HashMap<String,String>();
            map.put("full_name", String.valueOf(item.get("full_name")));
            map.put("html_url", String.valueOf(item.get("html_url")));
            map.put("description", String.valueOf(item.get("description")));
            map.put("star", String.valueOf(item.get("stargazers_count")));
            map.put("watch", String.valueOf(item.get("watchers_count")));
            map.put("fork", String.valueOf(item.get("forks_count")));
            array.add(map);
        }
        return array;
    }
}