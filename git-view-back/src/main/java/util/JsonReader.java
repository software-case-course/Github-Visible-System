package util;

import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonReader{
    public static String getJsonFile(InputStream in){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode node = objectMapper.readTree(in);
            return node.toString();
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }
}