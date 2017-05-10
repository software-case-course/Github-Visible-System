package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import po.AreaDetail;

public interface LocationDetailRepo extends CrudRepository<AreaDetail,Integer>{
    @SuppressWarnings("unchecked")
    public AreaDetail save(AreaDetail areaDetail);

    @Query("from AreaDetail a where a.location=:location")
    public List<AreaDetail> findByLocation(@Param("location")String location);

    @Query("from AreaDetail a where a.country=:country and a.country!=a.location")
    public List<AreaDetail> findByCountry(@Param("country")String country);

    @Query("from AreaDetail a where a.country=a.location")
    public List<AreaDetail> findCountries();

    @Modifying
    @Query("update AreaDetail a set a.users=:users where a.location=:location")
    public void updateUsers(@Param("location")String location, @Param("users")int users);
}