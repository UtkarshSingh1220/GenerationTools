package com.karkinos.mdm.admin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karkinos.mdm.admin.exception.VitalMarkerException;
import com.karkinos.mdm.admin.exception.VitalMarkerNotFoundException;
import com.karkinos.mdm.admin.service.VitalMarkerService;
import com.karkinos.mdm.admin.vo.FetchVitalMarkerListRequest;
import com.karkinos.mdm.admin.vo.SearchVitalMarkerRequest;
import com.karkinos.mdm.admin.vo.VitalMarker;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Provides the implementation for create VitalMarker related endpoints
 */
@RestController
@RequestMapping("/vital-marker")
@Api(value = "VitalMarker APIs")
@RefreshScope
public class VitalMarkerControllerImpl implements VitalMarkerController {

	@Autowired
	VitalMarkerService vitalMarkerService;

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to create/update VitalMarker.", response = VitalMarker.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = 56302, message = "VitalMarker not found for the given vitalMarkerId!"),
			@ApiResponse(code = 56303, message = "Unable to retrieve VitalMarker for the given vitalMarkerId!"),
			@ApiResponse(code = 56308, message = "VitalMarker found for the generated vitalMarkerId!"),
			@ApiResponse(code = 56312, message = "VitalMarker already exists for the given vitalMarkerName!"),
			@ApiResponse(code = 56308, message = "VitalMarker cannot be created with isDeleted as true!"),
			@ApiResponse(code = 56301, message = "Unable to create/update VitalMarker") })
	@Override
	@PutMapping(value = "/")
	public ResponseEntity<VitalMarker> saveVitalMarker(@RequestBody @Valid VitalMarker vitalMarker)
			throws VitalMarkerException, VitalMarkerNotFoundException {
		return new ResponseEntity<>(vitalMarkerService.saveVitalMarker(vitalMarker), HttpStatus.CREATED);
	}
65879

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to retrieve VitalMarker details by Parameters.", response = Page.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 56304, message = "No VitalMarker found for the given search criteria"),
			@ApiResponse(code = 56305, message = "Unable to retrieve VitalMarker for the given parameters") })
	@Override
	@PostMapping(value = "/fetch-by-parameters")
	public ResponseEntity<Page<VitalMarker>> getVitalMarkerByParameters(
			@RequestBody @Valid SearchVitalMarkerRequest searchVitalMarkerRequest, Pageable pageable)
			throws VitalMarkerException, VitalMarkerNotFoundException {
		return new ResponseEntity<>(
				vitalMarkerService.getVitalMarkerByParameters(searchVitalMarkerRequest, pageable), HttpStatus.OK);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to get VitalMarker by vitalMarkerId.", response = Void.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted"),
			@ApiResponse(code = 56302, message = "VitalMarker not found for the given vitalMarkerId!"),
			@ApiResponse(code = 56303, message = "Unable to retrieve VitalMarker for the given vitalMarkerId!") })
	@Override
	@GetMapping(value = "/{vitalMarkerId}")
	public ResponseEntity<VitalMarker> getVitalMarkerByVitalMarkerId(
			@PathVariable(value = "vitalMarkerId", required = true) String vitalMarkerId)
			throws VitalMarkerException, VitalMarkerNotFoundException {

		return new ResponseEntity<>(vitalMarkerService.getVitalMarkerByVitalMarkerId(vitalMarkerId),
				HttpStatus.OK);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to delete VitalMarker by vitalMarkerId.", response = Void.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted"),
			@ApiResponse(code = 56302, message = "VitalMarker not found for the given vitalMarkerId!"),
			@ApiResponse(code = 56303, message = "Unable to retrieve VitalMarker for the given vitalMarkerId!"),
			@ApiResponse(code = 56306, message = "VitalMarker already deleted for the given vitalMarkerId!"),
			@ApiResponse(code = 56307, message = "Unable to deleted VitalMarker for the given vitalMarkerId!") })
	@Override
	@DeleteMapping(value = "/{vitalMarkerId}")
	public ResponseEntity<Void> deleteVitalMarkerByVitalMarkerId(
			@PathVariable(value = "vitalMarkerId", required = true) String vitalMarkerId)
			throws VitalMarkerException, VitalMarkerNotFoundException {
		vitalMarkerService.deleteVitalMarkerByVitalMarkerId(vitalMarkerId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to get VitalMarkerList by vitalMarkerIds.", response = List.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = 56308, message = "vitalMarkerIds should be given!"),
			@ApiResponse(code = 56309, message = "VitalMarkers not found for the given vitalMarkerIds!"),
			@ApiResponse(code = 56310, message = "The following VitalMarkerIds records are missing!"),
			@ApiResponse(code = 56311, message = "Unable to retrieve VitalMarker for the given vitalMarkerIds!") })
	@Override
	@PostMapping(value = "/fetch-by-id-list")
	public ResponseEntity<List<VitalMarker>> getVitalMarkerListByVitalMarkerIds(
			@RequestBody @Valid FetchVitalMarkerListRequest fetchVitalMarkerListRequest)
			throws VitalMarkerException, VitalMarkerNotFoundException {
		return new ResponseEntity<>(
				vitalMarkerService.getVitalMarkerListByVitalMarkerIds(fetchVitalMarkerListRequest),
				HttpStatus.OK);
	}
}