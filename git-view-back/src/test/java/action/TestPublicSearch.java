// package action;

// import java.util.concurrent.ArrayBlockingQueue;
// import java.util.concurrent.ThreadPoolExecutor;
// import java.util.concurrent.TimeUnit;
// import java.lang.Runnable;

// import org.junit.Test;

// import po.Proxy;
// import timetask.ProxyPool;
// import util.RequestUtil;

// public class TestPublicSearch{
//     RequestUtil requestUtil;
//     ProxyPool proxyPool;

//     @Test
//     public void searchRepos() throws Exception{
//         requestUtil = new RequestUtil();
//         proxyPool = new ProxyPool();
//         String str = requestUtil.request("http://www.89ip.cn/api/?&tqsl=50&sxa=&sxb=&tta=&ports=&ktip=&cf=1");
//         String[] temp = str.split("\r\n");
//         String[] ips = temp[3].split("<br>")[0].split("<BR>");
//         ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50, 100, 1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(50));
//         for(String ip: ips){
//             String[] pp = ip.split(":");
//             final String ip1 = pp[0];
//             final int port1 = Integer.parseInt(pp[1]);
//             System.out.println(ip);
//             threadPool.execute(new Runnable(){
//                 public void run(){
//                     proxyPool.checkProxy(new Proxy(ip1,port1));
//                 }
//             });
//         }   
//     }
// }