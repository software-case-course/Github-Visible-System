// package service;

// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// import po.LanguageDetail;

// @RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = "classpath:applicationContext.xml")
// public class TestLanguageDetailService{
//     @Autowired
//     LanguageDetailService languageDetailService;

//     @Test
//     public void findByLanguage(){
//         LanguageDetail detail = languageDetailService.findByLanguage("Java");
//         System.out.println(detail.getRepo());
//     }
// }