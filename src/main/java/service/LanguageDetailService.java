package service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import po.LanguageDetail;
import repository.LanguageDetailRepo;

@Service
public class LanguageDetailService{
    @Autowired
    LanguageDetailRepo languageDetailRepo;

    public void setLanRepoCountRepo(LanguageDetailRepo languageDetailRepo){
        this.languageDetailRepo = languageDetailRepo;
    }

    public List<LanguageDetail> findAll(){
        return languageDetailRepo.findAll();
    }
    
    @Transactional
    public void updateRepo(String language, int repo){
        if(languageDetailRepo.findByLanguage(language)!=null) languageDetailRepo.updateRepo(language, repo);
        else {
            LanguageDetail detail = new LanguageDetail(language, repo, -1);
            languageDetailRepo.save(detail);
        }
    }

    @Transactional
    public void updateUsers(String language, int users){
        if(languageDetailRepo.findByLanguage(language)!=null) languageDetailRepo.updateUsers(language, users);
        else {
             LanguageDetail detail = new LanguageDetail(language, -1, users);
            languageDetailRepo.save(detail);
        }
    }

    public LanguageDetail findByLanguage(String language){
        return languageDetailRepo.findByLanguage(language);
    }
}