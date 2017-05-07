package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import po.YearDetail;

public interface YearDetailRepo extends CrudRepository<YearDetail,Integer>{
    public List<YearDetail> findAll();

    @Modifying
    @Query("update YearDetail a set a.repo=:repo where a.language=:language and a.year=:year")
    public void updateRepo(@Param("language")String language, @Param("year")int year, @Param("repo")int repo);

    @Modifying
    @Query("update YearDetail a set a.push=:push where a.language=:language and a.year=:year")
    public void updatePush(@Param("language")String language, @Param("year")int year, @Param("push")int push);

    @Query("select a from YearDetail a where a.language=:language and year=:year")
    public YearDetail findByLanguageAndYear(@Param("language")String language, @Param("year")int year);

    @Query("from YearDetail a where a.language=:language")
    public List<YearDetail> findByLanguage(@Param("language")String language);

    @Query("from YearDetail a where a.year=:year")
    public List<YearDetail> findByYear(@Param("year")int year);

    @SuppressWarnings("unchecked")
    public YearDetail save(YearDetail yearDetail);
}