package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.stereotype.Repository;

@Repository
public class RequestUtil{
    /**
     * @param requestUrl
     * @param requestMethod (GET or POST)
     * @param param (?=?&?=?)
     * @return sBuffer
     */
    public String requestForMap(String requestUrl, String requestMethod, String param, String mediaType){
        try{
            URL url = new URL(requestUrl);
            InputStream inputStream;
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            if(null!=param){
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(param);
                writer.flush();
            }
            if(null!=mediaType){
                conn.setRequestProperty("Accept", "application/vnd.github.cloak-preview");
            }
            conn.connect();
            System.out.println(conn.getResponseCode());
            if(conn.getResponseCode()!=HttpURLConnection.HTTP_OK
            &&conn.getResponseCode()!=HttpURLConnection.HTTP_ACCEPTED
            &&conn.getResponseCode()!=HttpURLConnection.HTTP_CREATED){
                inputStream = conn.getErrorStream();
            }
            else inputStream = conn.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
            String str = null;
            StringBuffer sBuffer = new StringBuffer();
            while((str = bufferedReader.readLine())!=null){
                sBuffer.append(str);
            }
            bufferedReader.close();
            inputStream.close();
            conn.disconnect();
            return sBuffer.toString();
        }catch(Exception e){
            System.out.println("connect fail");
            e.printStackTrace();
            return null;
        }
    }
}