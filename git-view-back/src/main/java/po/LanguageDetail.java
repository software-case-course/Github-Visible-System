package po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LanguageDetail{
    @GeneratedValue
    @Id
    Integer id;

    @Column(unique = true)
    String language;
    
    int repo;
    int users;

    public LanguageDetail(){}

    public LanguageDetail(String language, int repo, int users){
        this.language = language;
        this.repo = repo;
        this.users = users;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public void setRepo(int repo){
        this.repo = repo;
    }

    public void setUsers(int users){
        this.users = users;
    }

    public String getLanguage(){
        return this.language;
    }

    public int getRepo(){
        return this.repo;
    }

    public int getUsers(){
        return this.users;
    }
}