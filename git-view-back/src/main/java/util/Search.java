package util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;
import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.http.Request;
import com.jcabi.http.response.JsonResponse;

import po.Area;
import po.Language;

public class Search{
    static Github github = new RtGithub();

    public Search(){}

    public static int LanguageCount(Language language) throws IOException{
        System.out.println("Counting Repositories: "+ language.getValue() + "...");
        JsonObject json = ReposJson("language:"+language.getValue());
        int count = json.getInt("total_count");
        return count;
    }

    public static int LanguageYearCount(Language language, int year) throws IOException{
        System.out.println("Counting "+ year +" Year: "+ language.getValue() + "...");
        JsonObject json = github.entry().uri().path("/search/repositories")
            .queryParam("q", "language:"+language.getValue()+" created:<"+(year+1)+"-01-01")
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().readObject();
        int count = json.getInt("total_count");
        return count;
    }

    public static int LanguageYearPushCount(Language language, int year) throws IOException{
        System.out.println("Counting "+ year +" Push: "+ language.getValue() + "...");
        JsonObject json = ReposJson("language:"+language.getValue()+" pushed:"+(year)+"-01-01.."+(year+1)+"-01-01");
        int count = json.getInt("total_count");
        return count;
    }

    public static int LanguageUserCount(Language language) throws IOException{
        System.out.println("Counting Users: "+ language.getValue() + "...");
        JsonObject json = UserJson("language:"+language.getValue());
        int count = json.getInt("total_count");
        return count;
    }

    public static List<String> LanguageRepos(String language, int page, int per_page) throws IOException{
        List<String> repos = new ArrayList<String>();
        List<JsonObject> items = itemList("language:"+language, page, per_page);
        for(JsonObject item:items){
            System.out.println(item.getString("full_name"));          
            repos.add(item.getString("full_name"));
        }
        return repos;
    }

    public static int LocationUserCount(Area location) throws IOException{
        System.out.println("Counting Users in location: "+ location.getName() + "...");
        JsonObject json = UserJson("location:"+location.getValue());
        int count = json.getInt("total_count");
        return count;
    }

    public static JsonObject ReposJson(String q) throws IOException{
        return Json("repositories", q);
    }

    public static JsonObject ReposJson(String q, int page, int per_page) throws IOException{
        return Json("repositories", q, page, per_page);
    }

    public static List<JsonObject> itemList(String q, int page, int per_page) throws IOException{
        JsonObject json = ReposJson(q,page,per_page);
        return json.getJsonArray("items").getValuesAs(JsonObject.class);
    }

    public static JsonObject UserJson(String q) throws IOException{
        return Json("users", q);
    }

    private static JsonObject Json(String path, String q) throws IOException{
        return github.entry().uri().path("/search/" + path)
            .queryParam("q", q)
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().readObject();
    }

    private static JsonObject Json(String path, String q, int page, int per_page) throws IOException{
        return github.entry().uri().path("/search/" + path)
            .queryParam("q", q)
            .queryParam("page", page)
            .queryParam("per_page", per_page)
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().readObject();
    }
}