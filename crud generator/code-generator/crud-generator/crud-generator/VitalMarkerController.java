package com.karkinos.mdm.admin.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import com.karkinos.mdm.admin.exception.VitalMarkerException;
import com.karkinos.mdm.admin.exception.VitalMarkerNotFoundException;
import com.karkinos.mdm.admin.vo.FetchVitalMarkerListRequest;
import com.karkinos.mdm.admin.vo.SearchVitalMarkerRequest;
import com.karkinos.mdm.admin.vo.VitalMarker;

/**
 * This interface exposes the endpoints related to the VitalMarker.
 * 
 * @author utkarsh
 */
public interface VitalMarkerController {

	/**
	 * This API is used to create and update for VitalMarker.
	 * 
	 * @param VitalMarker
	 * @return VitalMarker
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public ResponseEntity<VitalMarker> saveVitalMarker(VitalMarker VitalMarker)
			throws VitalMarkerException, VitalMarkerNotFoundException;

	/**
	 * This API is used to get VitalMarker by VitalMarkerNameSearchable or get
	 * All VitalMarker.
	 * 
	 * @param searchVitalMarkerRequest
	 * @param pageable
	 * @return Page<VitalMarker>
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public ResponseEntity<Page<VitalMarker>> getVitalMarkerByParameters(
			SearchVitalMarkerRequest searchVitalMarkerRequest, Pageable pageable)
			throws VitalMarkerException, VitalMarkerNotFoundException;

	/**
	 * This API is used to get VitalMarker by vitalMarkerId.
	 * 
	 * @param vitalMarkerId
	 * @return VitalMarker
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public ResponseEntity<VitalMarker> getVitalMarkerByVitalMarkerId(String vitalMarkerId)
			throws VitalMarkerException, VitalMarkerNotFoundException;

	/**
	 * This API is used to delete VitalMarker by vitalMarkerId.
	 * 
	 * @param vitalMarkerId
	 * @return
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public ResponseEntity<Void> deleteVitalMarkerByVitalMarkerId(String vitalMarkerId)
			throws VitalMarkerException, VitalMarkerNotFoundException;

	/**
	 * This API is used to get VitalMarkerList By VitalMarkerIds
	 * 
	 * @param fetchVitalMarkerListRequest
	 * @return List<VitalMarker>
	 * @throws VitalMarkerException
	 * @throws VitalMarkerNotFoundException
	 */
	public ResponseEntity<List<VitalMarker>> getVitalMarkerListByVitalMarkerIds(
			FetchVitalMarkerListRequest fetchVitalMarkerListRequest)
			throws VitalMarkerException, VitalMarkerNotFoundException;

}