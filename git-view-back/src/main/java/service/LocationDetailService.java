package service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import po.AreaDetail;
import repository.LocationDetailRepo;

@Service
public class LocationDetailService{
    @Autowired
    LocationDetailRepo locationDetailRepo;

    @Transactional
    public void updateUsers(String location, String country, int users){
        if(locationDetailRepo.findByLocation(location)!=null) locationDetailRepo.updateUsers(location, users);
        else{
            AreaDetail detail = new AreaDetail(location,country,users);
            locationDetailRepo.save(detail);
        }
    }

    public AreaDetail findByLocation(String location){
        return locationDetailRepo.findByLocation(location);
    }

    public List<AreaDetail> findByCountry(String country){
        return locationDetailRepo.findByCountry(country);
    }

    public List<AreaDetail> findCountries(){
        return locationDetailRepo.findCountries();
    }
}