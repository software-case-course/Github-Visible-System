package util;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SearchDetail{
    final String DATE_FORMAT = "yyyy-MM-dd";
    @Autowired
    RequestUtil requestUtil;

    public String getContributorStat(String fullname){
        String result = null;
        try{
            result = searchStats(fullname, "contributors");
        }catch(Exception e){
            return "{error_message:" + e.getMessage() + "}";
        }
        if(result != null){
            JSONArray jsonArray = new JSONArray(result);
            Iterator<Object> iterator = jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject object = (JSONObject)iterator.next();
                JSONArray weeks = object.getJSONArray("weeks");
                Iterator<Object> weekIterator = weeks.iterator();
                while(weekIterator.hasNext()){
                    JSONObject weekObject = (JSONObject)weekIterator.next();
                    weekObject.put("w", TimeStamp2Date(String.valueOf(weekObject.getInt("w")), DATE_FORMAT));
                }
            }
            return jsonArray.toString();
        }
        return result;
    }

    public String getWeeklyCommit(String fullname){
        String result = null;
        try{
            result = searchStats(fullname, "commit_activity");
        }catch(Exception e){
            return "{error_message:" + e.getMessage() + "}";
        }
        if(result != null){
            JSONArray jsonArray = new JSONArray(result);
            Iterator<Object> iterator = jsonArray.iterator();
            JSONObject resultObject = new JSONObject();
            int sum0=0, sum1=0, sum2=0, sum3=0, sum4=0, sum5=0, sum6=0;
            while(iterator.hasNext()){
                JSONObject object = (JSONObject)iterator.next();
                sum0 += object.getJSONArray("days").getInt(0);
                sum1 += object.getJSONArray("days").getInt(1);
                sum2 += object.getJSONArray("days").getInt(2);
                sum3 += object.getJSONArray("days").getInt(3);
                sum4 += object.getJSONArray("days").getInt(4);
                sum5 += object.getJSONArray("days").getInt(5);
                sum6 += object.getJSONArray("days").getInt(6);
            }
            resultObject.put("Sunday", sum0);
            resultObject.put("Monday", sum1);
            resultObject.put("Tuesday", sum2);
            resultObject.put("Wednesday", sum3);
            resultObject.put("Thursday", sum4);
            resultObject.put("Friday", sum5);
            resultObject.put("Saturday", sum6);
            return resultObject.toString();
        }
        return result;
    }

    public String getCommitActivity(String fullname){
        String result = null;
        try{
            result = searchStats(fullname, "commit_activity");
        }catch(Exception e){
            return "{error_message:" + e.getMessage() + "}";
        }
        if(result != null){
            JSONArray jsonArray = new JSONArray(result);
            Iterator<Object> iterator = jsonArray.iterator();
            while(iterator.hasNext()){
                JSONObject object = (JSONObject)iterator.next();
                object.put("week", TimeStamp2Date(String.valueOf(object.getInt("week")), DATE_FORMAT));
                object.put("Sunday", String.valueOf(object.getJSONArray("days").getInt(0)));
                object.put("Monday", String.valueOf(object.getJSONArray("days").getInt(1)));
                object.put("Tuesday", String.valueOf(object.getJSONArray("days").getInt(2)));
                object.put("Wednesday", String.valueOf(object.getJSONArray("days").getInt(3)));
                object.put("Thursday", String.valueOf(object.getJSONArray("days").getInt(4)));
                object.put("Friday", String.valueOf(object.getJSONArray("days").getInt(5)));
                object.put("Saturday", String.valueOf(object.getJSONArray("days").getInt(6)));
                object.remove("days");
            }
            return jsonArray.toString();
        }
        return result;
    }

    public String getPunchCard(String fullname){
        String result = null;
        try{
            result = searchStats(fullname, "punch_card");
        }catch(Exception e){
            return "{error_message:" + e.getMessage() + "}";
        }
        if(result != null){
            JSONArray jsonArray = new JSONArray(result);
            Iterator<Object> iterator = jsonArray.iterator();
            JSONArray resultArray = new JSONArray();
            while(iterator.hasNext()){
                JSONArray array = (JSONArray)iterator.next();
                if(array.getInt(2)>0){
                    JSONObject object = new JSONObject();
                    object.put("day", array.getInt(0));
                    object.put("hour", array.getInt(1));
                    object.put("commit", array.getInt(2));
                    resultArray.put(object);
                }
            }
            return resultArray.toString();
        }
        return result;
    }

    public String getCodeFrequency(String fullname){
        String result = null;
        try{
            result = searchStats(fullname, "code_frequency");
        }catch(Exception e){
            return "{error_message:" + e + "}";
        }
        if(result != null){
            JSONArray jsonArray = new JSONArray(result);
            Iterator<Object> iterator = jsonArray.iterator();
            JSONArray resultArray = new JSONArray();
            while(iterator.hasNext()){
                JSONArray array = (JSONArray)iterator.next();
                JSONObject object = new JSONObject();
                object.put("week", TimeStamp2Date(String.valueOf(array.getInt(0)), DATE_FORMAT));
                object.put("additions", array.getInt(1));
                object.put("deletions", array.getInt(2));
                resultArray.put(object);
            }
            return resultArray.toString();
        }
        return result;
    }

    private String TimeStamp2Date(String timestampString, String formats){  
        Long timestamp = Long.parseLong(timestampString)*1000;  
        String date = new java.text.SimpleDateFormat(formats).format(new java.util.Date(timestamp));  
        return date;  
    }

   
    private String searchStats(String fullname, String part) throws Exception{
        String result = null;
        try{
            do{
                result = requestUtil.request("https://api.github.com/repos/"+fullname+"/stats/"+part);
            }while(result.startsWith("{"));
        }catch(Exception e){
            throw e;
        }
        return result;
    }
}