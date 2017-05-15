package service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import po.AreaDetail;
import po.AreaLanguage;
import repository.AreaLanguageRepo;
import repository.LocationDetailRepo;

@Service
public class AreaLanguageService{
    @Autowired
    AreaLanguageRepo areaLanguageRepo;

    @Autowired
    LocationDetailRepo locationDetailRepo;

    public List<AreaLanguage> findAll(){
        return areaLanguageRepo.findAll();
    }

    @Transactional
    public void updateUsers(String language, String location, int users){
        AreaDetail areaDetail = locationDetailRepo.findByLocation(location);
        if(areaDetail != null){
            if(areaLanguageRepo.findByAreaAndLanguage(areaDetail, language)!=null) areaLanguageRepo.updateUsers(language, areaDetail, users);
            else{
                AreaLanguage areaLanguage = new AreaLanguage(areaDetail, language, users);
                areaLanguageRepo.save(areaLanguage);
            }
        }
    }

    public List<AreaLanguage> findByLanguage(String language){
        return areaLanguageRepo.findByLanguage(language);
    }

    public List<AreaLanguage> findByArea(String location){
        AreaDetail areaDetail = locationDetailRepo.findByLocation(location);
        return areaLanguageRepo.findByArea(areaDetail);
    }

    public AreaLanguage findByAreaAndLanguage(String location, String language){
        AreaDetail areaDetail = locationDetailRepo.findByLocation(location);
        return areaLanguageRepo.findByAreaAndLanguage(areaDetail, language);
    }
}