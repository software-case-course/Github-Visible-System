package po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class YearDetail{
    @Id
    @GeneratedValue
    Integer id;

    String language;
    int year;
    int repo;
    int push;

    public YearDetail(){}

    public YearDetail(String language, int year, int repo, int push){
        this.language = language;
        this.year = year;
        this.repo = repo;
        this.push = push;
    }

    public void setLanguage(String language){
        this.language = language;
    }

    public void setYear(int year){
        this.year = year;
    }

    public void setRepo(int repo){
        this.repo = repo;
    }

    public void setPush(int push){
        this.push = push;
    }

    public String getLanguage(){
        return this.language;
    }

    public int getYear(){
        return this.year;
    }

    public int getRepo(){
        return this.repo;
    }

    public int getPush(){
        return this.push;
    }
}