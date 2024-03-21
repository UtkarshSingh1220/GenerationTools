package com.karkinos.mdm.admin.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.karkinos.mdm.admin.exception.VitalMarkerException;
import com.karkinos.mdm.admin.exception.VitalMarkerNotFoundException;
import com.karkinos.mdm.admin.model.VitalMarkerModel;
import com.karkinos.mdm.admin.repository.VitalMarkerRepository;
import com.karkinos.mdm.admin.util.MapperUtils;
import com.karkinos.mdm.admin.vo.FetchVitalMarkerListRequest;
import com.karkinos.mdm.admin.vo.SearchVitalMarkerRequest;
import com.karkinos.mdm.admin.vo.VitalMarker;


/**
 * The VitalMarker business logic interface
 * 
 * @author utkarsh singh
 */

public interface VitalMarkerService {

	/**
	 * This method is used to create and update for VitalMarker.
	 * 
	 * @param vitalMarker
	 * @return VitalMarker
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public VitalMarker saveVitalMarker(VitalMarker vitalMarker)
			throws VitalMarkerException, VitalMarkerNotFoundException;

	/**
	 * This method is used to get VitalMarker by VitalMarkerNameSearchable or
	 * get All VitalMarker.
	 * 
	 * @param searchVitalMarkerRequest
	 * @param pageable
	 * @return
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public Page<VitalMarker> getVitalMarkerByParameters(SearchVitalMarkerRequest searchVitalMarkerRequest,
			Pageable pageable) throws VitalMarkerException, VitalMarkerNotFoundException;

	/**
	 * This method is used to get VitalMarker by VitalMarkerId.
	 * 
	 * @param vitalMarkerId
	 * @return VitalMarker
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public VitalMarker getVitalMarkerByVitalMarkerId(String vitalMarkerId)
			throws VitalMarkerException, VitalMarkerNotFoundException;

	/**
	 * This method is used to delete VitalMarker by VitalMarkerId.
	 * 
	 * @param vitalMarkerId
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public void deleteVitalMarkerByVitalMarkerId(String vitalMarkerId)
			throws VitalMarkerException, VitalMarkerNotFoundException;

	/**
	 * This method is used to get VitalMarkerList By VitalMarkerIds
	 * 
	 * @param fetchVitalMarkerListRequest
	 * @return List<VitalMarker>
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public List<VitalMarker> getVitalMarkerListByVitalMarkerIds(
			FetchVitalMarkerListRequest fetchVitalMarkerListRequest)
			throws VitalMarkerException, VitalMarkerNotFoundException;

}
