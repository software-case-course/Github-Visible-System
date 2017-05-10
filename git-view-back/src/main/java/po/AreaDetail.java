package po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class AreaDetail{
    public static String CHINA = "china";
    public static String USA = "usa";

    @GeneratedValue
    @Id
    Integer id;

    @Column(unique = true)
    String location;
    
    String country;
    int users;

    public AreaDetail(){}

    public AreaDetail(String location, String country, int users){
        this.location = location;
        this.country = country;
        this.users = users;
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
}