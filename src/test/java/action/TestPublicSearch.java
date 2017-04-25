// package action;

// import java.io.IOException;

// import org.junit.Test;

// import util.RequestUtil;

// public class TestPublicSearch{
//     RequestUtil requestUtil;

//     @Test
//     public void searchRepos() throws Exception{
//         RequestUtil requestUtil = new RequestUtil();
//         for(int i=0;i<10;i++){
//             System.out.println(requestUtil.request(
//             "https://api.github.com/search/repositories?q=dsadwadawdas"));
//         }
//         System.out.println("next ip");
//         for(int i=0;i<11;i++){
//             System.out.println(requestUtil.request(
//             "https://api.github.com/search/repositories?q=dsadwadawdas",
//             "GET", null, null, requestUtil.getProxy("117.30.88.31", 47554)));
//         }
//     }
// }