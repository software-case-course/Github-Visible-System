package po;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class AreaDetail{

    @GeneratedValue
    @Id
    Integer id;

    @Column(unique = true)
    String location;
    
    String country;
    int users;

    @OneToMany(mappedBy = "areaDetail", fetch = FetchType.EAGER)
    @JsonBackReference
    Set<AreaLanguage> areaLanguagesSet;

    public AreaDetail(){}

    public AreaDetail(String location, String country, int users){
        this.location = location;
        this.country = country;
        this.users = users;
        areaLanguagesSet = new HashSet<AreaLanguage>();
    }

    public void setUsers(int users){
        this.users = users;
    }

    public int getUsers(){
        return this.users;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public String getLocation(){
        return this.location;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return this.country;
    }

    public void setAreaLanguagesSet(Set<AreaLanguage> areaLanguagesSet){
        this.areaLanguagesSet = areaLanguagesSet;
    }

    public Set<AreaLanguage> getAreaLanguagesSet(){
        return this.areaLanguagesSet;
    }
}