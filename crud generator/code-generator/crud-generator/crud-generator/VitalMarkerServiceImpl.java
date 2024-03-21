package com.karkinos.mdm.admin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import com.karkinos.mdm.admin.exception.VitalMarkerException;
import com.karkinos.mdm.admin.exception.VitalMarkerNotFoundException;
import com.karkinos.mdm.admin.model.VitalMarkerModel;
import com.karkinos.mdm.admin.repository.VitalMarkerRepository;
import com.karkinos.mdm.admin.util.MapperUtils;
import com.karkinos.mdm.admin.vo.FetchVitalMarkerListRequest;
import com.karkinos.mdm.admin.vo.SearchVitalMarkerRequest;
import com.karkinos.mdm.admin.vo.VitalMarker;

import lombok.extern.slf4j.Slf4j;

/**
 * The VitalMarker business logic implementation.
 */
@Service
@Slf4j
@RefreshScope
public class VitalMarkerServiceImpl implements VitalMarkerService {

	@Autowired
	VitalMarkerRepository vitalMarkerRepository;

	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public VitalMarker saveVitalMarker(VitalMarker vitalMarker)
			throws VitalMarkerException, VitalMarkerNotFoundException {
		try {
			VitalMarkerModel existingVitalMarkerForName = null;
			// check if vitalMarkerId is present update otherwise create
			if (StringUtils.isNotBlank(vitalMarker.getVitalMarkerId())) {
				VitalMarker existingVitalMarker = getVitalMarkerByVitalMarkerId(
						vitalMarker.getVitalMarkerId());
				String idForName = generateId(vitalMarker.getVitalMarkerName());
				if (!idForName.equals(existingVitalMarker.getVitalMarkerId())) {

					existingVitalMarkerForName = vitalMarkerRepository.findByVitalMarkerId(idForName);
					validateVitalMarkerByIdOrName(vitalMarker, existingVitalMarkerForName);
				}
				vitalMarker.setCreatedBy(existingVitalMarker.getCreatedBy());
				vitalMarker.setVersion(existingVitalMarker.getVersion());
				vitalMarker.setCreatedTime(existingVitalMarker.getCreatedTime());
			} else {
				if (vitalMarker.getIsDeleted()) {
					log.error(
							"VitalMarkerServiceImpl::saveVitalMarker:: VitalMarker cannot be created with isDeleted as true!");
					throw new VitalMarkerException(56308, "VitalMarker cannot be created with isDeleted as true");
				}
				// generate VitalMarkerId
				vitalMarker.setVitalMarkerId(generateId(vitalMarker.getVitalMarkerName()));
				existingVitalMarkerForName = vitalMarkerRepository
						.findByVitalMarkerName(vitalMarker.getVitalMarkerName());
				validateVitalMarkerByIdOrName(vitalMarker, existingVitalMarkerForName);
				// duplicate check for generated VitalMarkerId
				VitalMarkerModel vitalMarkerModel = vitalMarkerRepository
						.findByVitalMarkerId(vitalMarker.getVitalMarkerId());
				if (!Objects.isNull(vitalMarkerModel)) {
					if (!vitalMarkerModel.getIsDeleted()) {
						log.error(
								"VitalMarkerServiceImpl::saveVitalMarker:: VitalMarker found for the generated vitalMarkerId: {}!",
								vitalMarker.getVitalMarkerId());
						throw new VitalMarkerException(56300, vitalMarker.getVitalMarkerId());
					} else {
						// if VitalMarker is present for the generated VitalMarkerId and
						// status is DELETED update the status only as ACTIVE
						vitalMarkerModel.setIsDeleted(false);
						vitalMarker = MapperUtils.mapVitalMarker(vitalMarkerModel);
					}
				}

			}

			VitalMarkerModel saveVitalMarkerModel = MapperUtils.mapVitalMarkerModel(vitalMarker);
			saveVitalMarkerModel
					.setVitalMarkerNameSearchable(saveVitalMarkerModel.getVitalMarkerName().toLowerCase());
			return MapperUtils.mapVitalMarker(vitalMarkerRepository.save(saveVitalMarkerModel));
		} catch (VitalMarkerNotFoundException | VitalMarkerException vitalMarkerException) {
			throw vitalMarkerException;
		} catch (Exception exception) {
			log.error("VitalMarkerServiceImpl::saveVitalMarker:: Exception: Unable to create/update VitalMarker",
					exception);
			throw new VitalMarkerException(56301, "Unable to create/update VitalMarker");
		}
	}

	private void validateVitalMarkerByIdOrName(VitalMarker vitalMarker,
			VitalMarkerModel existingVitalMarkerForName) throws VitalMarkerException {
		if (!Objects.isNull(existingVitalMarkerForName)) {
			log.error(
					"VitalMarkerServiceImpl::saveVitalMarker:: VitalMarker already exists for the given vitalMarkerName: {}!",
					vitalMarker.getVitalMarkerName());
			throw new VitalMarkerException(56312, vitalMarker.getVitalMarkerName());
		}
	}

	private String generateId(String str) {
		return str.toUpperCase().replace(" ", "_").replace("&", "and");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<VitalMarker> getVitalMarkerByParameters(SearchVitalMarkerRequest searchVitalMarkerRequest,
			Pageable pageable) throws VitalMarkerException, VitalMarkerNotFoundException {
		try {
			List<VitalMarkerModel> VitalMarkerModels = null;
			List<Criteria> criterias = new ArrayList<>();
			// check if SearchVitalMarkerRequest is not null
			if (!Objects.isNull(searchVitalMarkerRequest)) {
				// if VitalMarkerId is present add it in criteria
				if (StringUtils.isNotBlank(searchVitalMarkerRequest.getVitalMarkerNameSearchable())) {
					criterias.add(Criteria.where("vitalMarkerNameSearchable")
							.regex(searchVitalMarkerRequest.getVitalMarkerNameSearchable().toLowerCase()));
				}
				// if status is present add it in criteria
				if (searchVitalMarkerRequest.getIsDeleted() != null) {
					criterias.add(Criteria.where("isDeleted").is(searchVitalMarkerRequest.getIsDeleted()));
				}
			}
			Query query = new Query();
			query.with(pageable);
			if (CollectionUtils.isNotEmpty(criterias)) {
				query.addCriteria((new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))));
			}
			VitalMarkerModels = mongoTemplate.find(query, VitalMarkerModel.class);
			// check if VitalMarker found for the given criteria otherwise throw
			// exception
			//if (CollectionUtils.isEmpty(VitalMarkerModels)) {
				//log.error(
						//"VitalMarkerServiceImpl::getVitalMarkerByParameters:: No VitalMarker found for the given search criteria");
				//throw new VitalMarkerNotFoundException(56304, "No VitalMarker found for the given search criteria");
			//}
			return MapperUtils.mapVitalMarkerPages(PageableExecutionUtils.getPage(VitalMarkerModels, pageable,
					() -> mongoTemplate.count(query.limit(-1).skip(-1), VitalMarkerModel.class)));
		} /*catch (VitalMarkerNotFoundException vitalMarkerNotFoundException) {
			throw vitalMarkerNotFoundException;
		} */catch (Exception exception) {
			log.error(
					"VitalMarkerServiceImpl::getVitalMarkerByParameters:: Unable to retrieve VitalMarker for the given parameters",
					exception);
			throw new VitalMarkerNotFoundException(56305,
					"Unable to retrieve VitalMarker for the given parameters");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VitalMarker getVitalMarkerByVitalMarkerId(String vitalMarkerId)
			throws VitalMarkerException, VitalMarkerNotFoundException {
		try {
			// check if VitalMarker found for the given VitalMarkerId otherwise
			// throw exception
			VitalMarkerModel vitalMarkerModel = vitalMarkerRepository.findByVitalMarkerId(vitalMarkerId);
			if (Objects.isNull(vitalMarkerModel)) {
				log.error(
						"VitalMarkerServiceImpl::getVitalMarkerByVitalMarkerId:: VitalMarker not found for the given vitalMarkerId: {}!",
						vitalMarkerId);
				throw new VitalMarkerNotFoundException(56302, vitalMarkerId);
			}
			return MapperUtils.mapVitalMarker(vitalMarkerModel);
		} catch (VitalMarkerNotFoundException vitalMarkerNotFoundException) {
			throw vitalMarkerNotFoundException;
		} catch (Exception exception) {
			log.error(
					"VitalMarkerServiceImpl::getVitalMarkerByVitalMarkerId:: Unable to retrieve VitalMarker for the given vitalMarkerId: {0}!",
					vitalMarkerId, exception);
			throw new VitalMarkerException(56303, vitalMarkerId);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteVitalMarkerByVitalMarkerId(String vitalMarkerId)
			throws VitalMarkerException, VitalMarkerNotFoundException {
		try {
			// check if VitalMarker found for the given VitalMarkerId and status
			// is not DELETED otherwise throw exception
			VitalMarker vitalMarker = getVitalMarkerByVitalMarkerId(vitalMarkerId);
			if (Objects.isNull(vitalMarker) || vitalMarker.getIsDeleted()) {
				log.error(
						"VitalMarkerServiceImpl::deleteVitalMarkerByVitalMarkerId:: VitalMarker already deleted for the given vitalMarkerId: {}!",
						vitalMarkerId);
				throw new VitalMarkerNotFoundException(56306, vitalMarkerId);
			}
			vitalMarker.setIsDeleted(true);
			vitalMarkerRepository.save(MapperUtils.mapVitalMarkerModel(vitalMarker));
		} catch (VitalMarkerNotFoundException vitalMarkerNotFoundException) {
			throw vitalMarkerNotFoundException;
		} catch (Exception exception) {
			log.error(
					"VitalMarkerServiceImpl::deleteVitalMarkerByVitalMarkerId:: Unable to deleted VitalMarker for the given vitalMarkerId: {}!",
					vitalMarkerId, exception);
			throw new VitalMarkerException(56307, vitalMarkerId);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<VitalMarker> getVitalMarkerListByVitalMarkerIds(
			FetchVitalMarkerListRequest fetchVitalMarkerListRequest)
			throws VitalMarkerNotFoundException, VitalMarkerException {
		List<String> requestedVitalMarkerIds = new ArrayList<>();
		try {
			if (CollectionUtils.isEmpty(fetchVitalMarkerListRequest.getVitalMarkerIdList())) {
				log.error(
						"VitalMarkerServiceImpl::getVitalMarkerListByVitalMarkerIds:: vitalMarkerIds should be given!");
				throw new VitalMarkerNotFoundException(56308, "vitalMarkerIds should be given!");
			}
			requestedVitalMarkerIds = fetchVitalMarkerListRequest.getVitalMarkerIdList().stream().distinct()
					.collect(Collectors.toList());
			List<String> vitalMarkerIds = new ArrayList<>();
			List<VitalMarkerModel> vitalMarkerModelList = vitalMarkerRepository
					.findByVitalMarkerIdIn(requestedVitalMarkerIds);
			if (CollectionUtils.isEmpty(vitalMarkerModelList)) {
				log.error(
						"VitalMarkerServiceImpl::getVitalMarkerListByVitalMarkerIds:: VitalMarkers not found for the given vitalMarkerIds: {}!",
						requestedVitalMarkerIds.toString());
				throw new VitalMarkerNotFoundException(56309,
						"VitalMarkers not found for the given vitalMarkerIds!",
						requestedVitalMarkerIds.toString());
			}

			if (vitalMarkerModelList.size() < requestedVitalMarkerIds.size()) {
				for (VitalMarkerModel vitalMarkerModel : vitalMarkerModelList) {
					vitalMarkerIds.add(vitalMarkerModel.getVitalMarkerId());
				}
				requestedVitalMarkerIds.removeAll(vitalMarkerIds);
				log.error(
						"VitalMarkerServiceImpl::getVitalMarkerListByVitalMarkerIds:: The following VitalMarkerIds: {} records are missing!",
						requestedVitalMarkerIds.toString());
				throw new VitalMarkerNotFoundException(56310, requestedVitalMarkerIds.toString());
			}
			return MapperUtils.mapVitalMarkerVOs(vitalMarkerModelList);
		} catch (VitalMarkerNotFoundException vitalMarkerNotFoundException) {
			throw vitalMarkerNotFoundException;
		} catch (Exception exception) {
			log.error(
					"VitalMarkerServiceImpl::getVitalMarkerByTaskStatusAndVitalMarkerId:: Unable to retrieve VitalMarker for the given vitalMarkerIds: {}!",
					requestedVitalMarkerIds.toString(), exception);
			throw new VitalMarkerException(56311, requestedVitalMarkerIds.toString());
		}
	}

}