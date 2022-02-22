/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.Controller;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import payroll.Entity.Signalement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Dell
 */
@Transactional
interface SignalRepository extends CrudRepository<Signalement, Integer>,JpaRepository <Signalement, Integer>  {
    List<Signalement> findByIdstatut(Integer idstatut);
    
    List<Signalement> findByIdregion(Integer idregion);
    
    List<Signalement> findByIdpersonne(Integer idpersonne);
    
    List<Signalement> findByIdstatutAndIdregion(Integer idstatut,Integer idregion);
    
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where s.idstatut= :idstatut or s.idTypesignalement= :type or s.dateheure<=:dateh and s.dateheure>=:datee", nativeQuery=true)
    List<Signalement> getSign(@Param("idregion") Integer region,@Param("idstatut") Integer statut, @Param("type") Integer type, @Param("dateh") Date date,@Param("datee") Date date1);
    
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where s.idstatut= :idstatut or s.idTypesignalement= :type", nativeQuery=true)
    List<Signalement> getSignSansDate(@Param("idregion") Integer region,@Param("idstatut") Integer statut, @Param("type") Integer type);
    
    //select idstatut
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where s.idstatut= :idstatut", nativeQuery=true)
    List<Signalement> getSignSansDateStatut(@Param("idregion") Integer region,@Param("idstatut") Integer statut);
    
    //select idtype
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where s.idTypesignalement= :type", nativeQuery=true)
    List<Signalement> getSignSansDatetype(@Param("idregion") Integer region,@Param("type") Integer type);

        //select d1,d2
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where dateheure BETWEEN :dateh and :datee", nativeQuery=true)
    List<Signalement> getSignDate(@Param("idregion") Integer region, @Param("dateh") Date date,@Param("datee") Date date1);
    
    //select d1,d2,idstatut
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where s.idstatut= :idstatut or dateheure BETWEEN :dateh and :datee", nativeQuery=true)
    List<Signalement> getSignDateStatut(@Param("idregion") Integer region,@Param("idstatut") Integer statut, @Param("dateh") Date date,@Param("datee") Date date1);
    
     //select d1,d2,idtype
    @Modifying
    @Query(value="select * from (select * from signalement a where a.idregion=:idregion) s where s.idTypesignalement= :type and dateheure BETWEEN :dateh and :datee", nativeQuery=true)
    List<Signalement> getSignDateType(@Param("idregion") Integer region,@Param("type") Integer type, @Param("dateh") Date date,@Param("datee") Date date1);
    
    
    
}
