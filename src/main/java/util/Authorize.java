package util;

import java.io.IOException;

import javax.json.JsonObject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import com.jcabi.github.Github;
import com.jcabi.github.RtGithub;
import com.jcabi.http.Request;
import com.jcabi.http.request.ApacheRequest;
import com.jcabi.http.response.JsonResponse;
import com.jcabi.manifests.Manifests;

public class Authorize{
    static final String USER_AGENT = String.format(
        "jcabi-github %s %s %s",
        Manifests.read("JCabi-Version"),
        Manifests.read("JCabi-Build"),
        Manifests.read("JCabi-Date")
    );

    static final Request REQUEST =
        new ApacheRequest("https://github.com")
            .header(HttpHeaders.USER_AGENT, USER_AGENT)
            .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

    static Github github = new RtGithub(REQUEST);

    public static String getToken(String code) throws IOException{
        JsonObject json = github.entry().uri().path("/login/oauth/access_token")
            .queryParam("client_id", "2687357014dac98c13a9")
            .queryParam("client_secret", "e6808532ec8db2b38dacf1a5514589200476e242")
            .queryParam("code", code)
            .back().method(Request.GET).fetch().as(JsonResponse.class).json().readObject();
        return json.getString("access_token");
    }
}