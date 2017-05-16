package po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class AreaLanguage{
    @GeneratedValue
    @Id
    Integer id;

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "area_id")
    AreaDetail areaDetail;
    
    String language;
    int users;

    public AreaLanguage(){}

    public AreaLanguage(AreaDetail areaDetail, String language, int users){
        this.areaDetail = areaDetail;
        this.language = language;
        this.users = users;
    }

    public void setUsers(int users){
        this.users = users;
    }

    public int getUsers(){
        return this.users;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public String getLanguage(){
        return this.language;
    }

    public void setAreaDetail(AreaDetail areaDetail){
        this.areaDetail = areaDetail;
    }

    public AreaDetail getAreaDetail(){
        return this.areaDetail;
    }
}