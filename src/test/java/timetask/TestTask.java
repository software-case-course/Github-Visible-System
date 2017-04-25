// package timetask;

// import java.io.IOException;
// import java.util.Iterator;

// import org.json.JSONArray;
// import org.json.JSONObject;
// import org.junit.Test;

// import util.RequestUtil;

// public class TestTask{

//     @Test
//     public void climb() throws IOException{
//         RequestUtil requestUtil = new RequestUtil();
//         String result = requestUtil.request("http://www.xdaili.cn/ipagent//freeip/getFreeIps");
//         JSONObject json = requestUtil.StringToJson(result);
//         JSONArray rows = json.getJSONArray("rows");
//         Iterator<Object> iterator = rows.iterator();
//         while(iterator.hasNext()){
//             JSONObject row = (JSONObject)iterator.next();
//             System.out.println(row.get("ip")+":"+row.get("port"));
//         }
//     }
// }