package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;

public class Relative {
    static Github github = new RtGithub();

    public Relative () {}

    public static List<String> getRelativeRepos(String repo) throws IOException{
        int count = 5;
        int x = 0;
        JsonArray starers = stargazers(repo);
        Map<String,Integer> repoMap = new HashMap<String,Integer>();
        Iterator<JsonValue> iterator = starers.iterator();
        while (iterator.hasNext()) {
            JsonObject starer = (JsonObject)iterator.next();
            String name = starer.getString("login");
            JsonArray relativeRepos = stars(name);
            Iterator<JsonValue> relativeIter = relativeRepos.iterator();
            while (relativeIter.hasNext()) {
                JsonObject oneRepo = (JsonObject)relativeIter.next();
                String repoName = oneRepo.getString("full_name");
                System.out.println(x++);
                if(repoMap.containsKey(repoName)){
                    repoMap.put(repoName, repoMap.get(repoName)+1);
                }
                else repoMap.put(repoName, 1);
            }
        }
        return getMaxKey(repoMap, count);
    }

    public static JsonArray followers(String repo) throws IOException{
        return users(repo, "followers");
    }

    public static JsonArray following(String user) throws IOException{
        return users(user, "following");
    }

    public static JsonArray stars(String user) throws IOException{
        return users(user, "starred");
    }

    public static JsonArray stargazers(String repo) throws IOException{
        return github.entry().uri().path("/repos/" + repo + "/stargazers")
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().readArray();
    }

    public static JsonArray users(String user, String path) throws IOException{
        return github.entry().uri().path("/users/" + user + "/" + path)
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().readArray();
    }

    public static List<String> getMaxKey(Map<String,Integer> repoMap, int count) {
        List<String> result = new ArrayList<String>();
        List<Entry<String,Integer>> list = new ArrayList<Entry<String,Integer>>(repoMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String,Integer>>(){
            public int compare(Map.Entry<String, Integer> o1,Map.Entry<String, Integer> o2){
                return (o2.getValue() - o1.getValue());
            }
        });
        for(int i=0;i<count;i++){
            result.add(list.get(i).getKey());
        }
        return result;
    }

    public static JsonArray authorRepos(String token) throws IOException{
        return github.entry().uri().path("/user/repos")
            .queryParam("access_token", token)
            .queryParam("sort", "pushed")
            .queryParam("direction", "asc")
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().readArray();
    }

    public static JsonObject languages(String repo, String token) throws IOException{
        return github.entry().uri().path("/repos/" + repo + "/languages")
            .queryParam("access_token", token)
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().readObject();
    }

    public static Map<String,Integer> codes(String token) throws IOException{
        Map<String,Integer> result = new HashMap<String,Integer>();
        JsonArray repos = null;
        try{
            repos = authorRepos(token);
        }catch(Exception e){
            result.put("error_code", 401);
            return result;
        }
        Iterator<JsonValue> iterator = repos.iterator();
        while (iterator.hasNext()) {
            JsonObject repo = (JsonObject)iterator.next();
            String fullname = repo.getString("full_name");
            JsonObject obj = languages(fullname, token);
            for(String key: obj.keySet()){
                if(result.containsKey(key)){
                    result.put(key, result.get(key)+obj.getInt(key));
                }
                else result.put(key, obj.getInt(key));
            }
        }
        return result;
    }
}