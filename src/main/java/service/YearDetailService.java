package service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import po.YearDetail;
import repository.YearDetailRepo;

@Service
public class YearDetailService{
    @Autowired
    YearDetailRepo yearDetailRepo;

    public List<YearDetail> findAll(){
        return yearDetailRepo.findAll();
    }

    @Transactional
    public void updateRepo(String language, int year, int repo){
        if(yearDetailRepo.findByLanguageAndYear(language, year)!=null) yearDetailRepo.updateRepo(language, year, repo);
        else {
            YearDetail detail = new YearDetail(language, year, repo, -1);
            yearDetailRepo.save(detail);
        }
    }

    @Transactional
    public void updatePush(String language, int year, int push){
        if(yearDetailRepo.findByLanguageAndYear(language, year)!=null) yearDetailRepo.updatePush(language, year, push);
        else {
            YearDetail detail = new YearDetail(language, year, -1, push);
            yearDetailRepo.save(detail);
        }
    }

    public YearDetail findByLanguageAndYear(String language, int year){
        return yearDetailRepo.findByLanguageAndYear(language, year);
    }

    public List<YearDetail> findByLanguage(String language){
        return yearDetailRepo.findByLanguage(language);
    }

    public List<YearDetail> findByYear(int year){
        return yearDetailRepo.findByYear(year);
    }
}