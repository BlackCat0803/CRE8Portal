package com.pharma.core.repository.physician;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pharma.core.model.physician.PhysicianProfileInfo;
/**
 * This class is a Java interface that defines a data access contract
 *
 */
@Repository("PhysicianProfileInfo")
public interface PhysicianProfileRespository extends JpaRepository<PhysicianProfileInfo, Integer> {
	
	List<PhysicianProfileInfo> findByDea(String dea);
	
	List<PhysicianProfileInfo> findByDeaAndPhysicianIdNot(String dea, int id);
	
	@Query("select p from PhysicianProfileInfo p, PhysicianAccount i where i.id=p.physicianId and i.state=:state and p.stateLicense=:license ")
	List<PhysicianProfileInfo> findByStateLicense(@Param("license") String license, @Param("state") String state);
	
	@Query("select p from PhysicianProfileInfo p, PhysicianAccount i where i.id=p.physicianId and i.state=:state and p.stateLicense=:license and i.id!=:id ")
	List<PhysicianProfileInfo> findByStateLicenseAndPhysicianIdNot(@Param("license") String license, @Param("state") String state, @Param("id") int id);

	
	// Check duplicate records for DEA with Group 
	@Query("select p from PhysicianProfileInfo p left join PhysicianGroup gph on gph.physicianId=p.physicianId left join GroupMaster g on g.id=gph.groupId "
			+ "where p.dea=:dea and g.id=:groupId ")
	List<PhysicianProfileInfo> findByDeaWithGroup(@Param("dea") String dea, @Param("groupId") int groupId);
	
	@Query("select p from PhysicianProfileInfo p left join PhysicianGroup gph on gph.physicianId=p.physicianId left join GroupMaster g on g.id=gph.groupId "
			+ "where p.dea=:dea and g.id=:groupId and p.physicianId != :physicianId ")
	List<PhysicianProfileInfo> findByDeaWithGroup(@Param("physicianId") int physicianId, @Param("dea") String dea, @Param("groupId") int groupId);

}
