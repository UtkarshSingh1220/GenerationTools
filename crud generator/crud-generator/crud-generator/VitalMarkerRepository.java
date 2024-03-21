package com.karkinos.mdm.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.karkinos.mdm.admin.pg.model.VitalMarkerModel;


@Repository
public interface VitalMarkerRepository extends JpaRepository<VitalMarkerModel, String> {
	VitalMarkerModel findByVitalMarkerId(String vitalMarkerId);

	List<VitalMarkerModel> findByVitalMarkerIdIn(List<String> vitalMarkerIdList);

	VitalMarkerModel findByVitalMarkerName(String vitalMarkerName);

}
