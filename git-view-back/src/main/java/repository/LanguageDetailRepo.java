package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import po.LanguageDetail;

public interface LanguageDetailRepo extends CrudRepository<LanguageDetail,Integer>{
    public List<LanguageDetail> findAll();
    
    @Modifying
    @Query("update LanguageDetail a set a.repo=:repo where a.language=:language")
    public void updateRepo(@Param("language")String language, @Param("repo")int repo);

    @Modifying
    @Query("update LanguageDetail a set a.users=:users where a.language=:language")
    public void updateUsers(@Param("language")String language, @Param("users")int users);

    @Query("select a from LanguageDetail a where a.language=:language")
    public LanguageDetail findByLanguage(@Param("language")String language);

    @SuppressWarnings("unchecked")
    public LanguageDetail save(LanguageDetail languageDetail);
}