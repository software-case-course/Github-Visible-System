package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import po.AreaDetail;
import po.AreaLanguage;

public interface AreaLanguageRepo extends CrudRepository<AreaLanguage,Integer>{
    public List<AreaLanguage> findAll();

    @SuppressWarnings("unchecked")
    public AreaLanguage save(AreaLanguage areaLanguage);

    @Query("from AreaLanguage a where a.language=:language")
    public List<AreaLanguage> findByLanguage(@Param("language")String language);

    @Query("from AreaLanguage a where a.areaDetail=:areaDetail")
    public List<AreaLanguage> findByArea(@Param("areaDetail")AreaDetail areaDetail);

    @Query("Select a from AreaLanguage a where a.areaDetail=:areaDetail and a.language=:language")
    public AreaLanguage findByAreaAndLanguage(@Param("areaDetail")AreaDetail areaDetail, @Param("language")String language);

    @Modifying
    @Query("update AreaLanguage a set a.users=:users where a.language=:language and a.areaDetail=:areaDetail")
    public void updateUsers(@Param("language")String language, @Param("areaDetail")AreaDetail areaDetail, @Param("users")int users);
}