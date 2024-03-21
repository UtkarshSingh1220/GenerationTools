package {{ROOT_PACKAGE}}.service;

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

import {{ROOT_PACKAGE}}.exception.{{VONAME}}Exception;
import {{ROOT_PACKAGE}}.exception.{{VONAME}}NotFoundException;
import {{ROOT_PACKAGE}}.model.{{VONAME}}Model;
import {{ROOT_PACKAGE}}.repository.{{VONAME}}Repository;
import {{ROOT_PACKAGE}}.util.MapperUtils;
import {{ROOT_PACKAGE}}.vo.Fetch{{VONAME}}ListRequest;
import {{ROOT_PACKAGE}}.vo.Search{{VONAME}}Request;
import {{ROOT_PACKAGE}}.vo.{{VONAME}};

import lombok.extern.slf4j.Slf4j;

/**
 * The {{VONAME}} business logic implementation.
 */
@Service
@Slf4j
@RefreshScope
public class {{VONAME}}ServiceImpl implements {{VONAME}}Service {

	@Autowired
	{{VONAME}}Repository {{VONAME_CAMELCASE}}Repository;

	@Autowired
	MongoTemplate mongoTemplate;

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public {{VONAME}} save{{VONAME}}({{VONAME}} {{VONAME_CAMELCASE}})
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException {
		try {
			{{VONAME}}Model existing{{VONAME}}ForName = null;
			// check if {{VONAME_CAMELCASE}}Id is present update otherwise create
			if (StringUtils.isNotBlank({{VONAME_CAMELCASE}}.get{{VONAME}}Id())) {
				{{VONAME}} existing{{VONAME}} = get{{VONAME}}By{{VONAME}}Id(
						{{VONAME_CAMELCASE}}.get{{VONAME}}Id());
				String idForName = generateId({{VONAME_CAMELCASE}}.get{{VONAME}}Name());
				if (!idForName.equals(existing{{VONAME}}.get{{VONAME}}Id())) {

					existing{{VONAME}}ForName = {{VONAME_CAMELCASE}}Repository.findBy{{VONAME}}Id(idForName);
					validate{{VONAME}}ByIdOrName({{VONAME_CAMELCASE}}, existing{{VONAME}}ForName);
				}
				{{VONAME_CAMELCASE}}.setCreatedBy(existing{{VONAME}}.getCreatedBy());
				{{VONAME_CAMELCASE}}.setVersion(existing{{VONAME}}.getVersion());
				{{VONAME_CAMELCASE}}.setCreatedTime(existing{{VONAME}}.getCreatedTime());
			} else {
				if ({{VONAME_CAMELCASE}}.getIsDeleted()) {
					log.error(
							"{{VONAME}}ServiceImpl::save{{VONAME}}:: {{VONAME}} cannot be created with isDeleted as true!");
					throw new {{VONAME}}Exception({{BASE_ERROR_CODE}}+8, "{{VONAME}} cannot be created with isDeleted as true");
				}
				// generate {{VONAME}}Id
				{{VONAME_CAMELCASE}}.set{{VONAME}}Id(generateId({{VONAME_CAMELCASE}}.get{{VONAME}}Name()));
				existing{{VONAME}}ForName = {{VONAME_CAMELCASE}}Repository
						.findBy{{VONAME}}Name({{VONAME_CAMELCASE}}.get{{VONAME}}Name());
				validate{{VONAME}}ByIdOrName({{VONAME_CAMELCASE}}, existing{{VONAME}}ForName);
				// duplicate check for generated {{VONAME}}Id
				{{VONAME}}Model {{VONAME_CAMELCASE}}Model = {{VONAME_CAMELCASE}}Repository
						.findBy{{VONAME}}Id({{VONAME_CAMELCASE}}.get{{VONAME}}Id());
				if (!Objects.isNull({{VONAME_CAMELCASE}}Model)) {
					if (!{{VONAME_CAMELCASE}}Model.getIsDeleted()) {
						log.error(
								"{{VONAME}}ServiceImpl::save{{VONAME}}:: {{VONAME}} found for the generated {{VONAME_CAMELCASE}}Id: {}!",
								{{VONAME_CAMELCASE}}.get{{VONAME}}Id());
						throw new {{VONAME}}Exception({{BASE_ERROR_CODE}}+0, {{VONAME_CAMELCASE}}.get{{VONAME}}Id());
					} else {
						// if {{VONAME}} is present for the generated {{VONAME}}Id and
						// status is DELETED update the status only as ACTIVE
						{{VONAME_CAMELCASE}}Model.setIsDeleted(false);
						{{VONAME_CAMELCASE}} = MapperUtils.map{{VONAME}}({{VONAME_CAMELCASE}}Model);
					}
				}

			}

			{{VONAME}}Model save{{VONAME}}Model = MapperUtils.map{{VONAME}}Model({{VONAME_CAMELCASE}});
			save{{VONAME}}Model
					.set{{VONAME}}NameSearchable(save{{VONAME}}Model.get{{VONAME}}Name().toLowerCase());
			return MapperUtils.map{{VONAME}}({{VONAME_CAMELCASE}}Repository.save(save{{VONAME}}Model));
		} catch ({{VONAME}}NotFoundException | {{VONAME}}Exception {{VONAME_CAMELCASE}}Exception) {
			throw {{VONAME_CAMELCASE}}Exception;
		} catch (Exception exception) {
			log.error("{{VONAME}}ServiceImpl::save{{VONAME}}:: Exception: Unable to create/update {{VONAME}}",
					exception);
			throw new {{VONAME}}Exception({{BASE_ERROR_CODE}}+1, "Unable to create/update {{VONAME}}");
		}
	}

	private void validate{{VONAME}}ByIdOrName({{VONAME}} {{VONAME_CAMELCASE}},
			{{VONAME}}Model existing{{VONAME}}ForName) throws {{VONAME}}Exception {
		if (!Objects.isNull(existing{{VONAME}}ForName)) {
			log.error(
					"{{VONAME}}ServiceImpl::save{{VONAME}}:: {{VONAME}} already exists for the given {{VONAME_CAMELCASE}}Name: {}!",
					{{VONAME_CAMELCASE}}.get{{VONAME}}Name());
			throw new {{VONAME}}Exception({{BASE_ERROR_CODE}}+12, {{VONAME_CAMELCASE}}.get{{VONAME}}Name());
		}
	}

	private String generateId(String str) {
		return str.toUpperCase().replace(" ", "_").replace("&", "and");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<{{VONAME}}> get{{VONAME}}ByParameters(Search{{VONAME}}Request search{{VONAME}}Request,
			Pageable pageable) throws {{VONAME}}Exception, {{VONAME}}NotFoundException {
		try {
			List<{{VONAME}}Model> {{VONAME}}Models = null;
			List<Criteria> criterias = new ArrayList<>();
			// check if Search{{VONAME}}Request is not null
			if (!Objects.isNull(search{{VONAME}}Request)) {
				// if {{VONAME}}Id is present add it in criteria
				if (StringUtils.isNotBlank(search{{VONAME}}Request.get{{VONAME}}NameSearchable())) {
					criterias.add(Criteria.where("{{VONAME_CAMELCASE}}NameSearchable")
							.regex(search{{VONAME}}Request.get{{VONAME}}NameSearchable().toLowerCase()));
				}
				// if status is present add it in criteria
				if (search{{VONAME}}Request.getIsDeleted() != null) {
					criterias.add(Criteria.where("isDeleted").is(search{{VONAME}}Request.getIsDeleted()));
				}
			}
			Query query = new Query();
			query.with(pageable);
			if (CollectionUtils.isNotEmpty(criterias)) {
				query.addCriteria((new Criteria().andOperator(criterias.toArray(new Criteria[criterias.size()]))));
			}
			{{VONAME}}Models = mongoTemplate.find(query, {{VONAME}}Model.class);
			// check if {{VONAME}} found for the given criteria otherwise throw
			// exception
			//if (CollectionUtils.isEmpty({{VONAME}}Models)) {
				//log.error(
						//"{{VONAME}}ServiceImpl::get{{VONAME}}ByParameters:: No {{VONAME}} found for the given search criteria");
				//throw new {{VONAME}}NotFoundException({{BASE_ERROR_CODE}}+4, "No {{VONAME}} found for the given search criteria");
			//}
			return MapperUtils.map{{VONAME}}Pages(PageableExecutionUtils.getPage({{VONAME}}Models, pageable,
					() -> mongoTemplate.count(query.limit(-1).skip(-1), {{VONAME}}Model.class)));
		} /*catch ({{VONAME}}NotFoundException {{VONAME_CAMELCASE}}NotFoundException) {
			throw {{VONAME_CAMELCASE}}NotFoundException;
		} */catch (Exception exception) {
			log.error(
					"{{VONAME}}ServiceImpl::get{{VONAME}}ByParameters:: Unable to retrieve {{VONAME}} for the given parameters",
					exception);
			throw new {{VONAME}}NotFoundException({{BASE_ERROR_CODE}}+5,
					"Unable to retrieve {{VONAME}} for the given parameters");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public {{VONAME}} get{{VONAME}}By{{VONAME}}Id(String {{VONAME_CAMELCASE}}Id)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException {
		try {
			// check if {{VONAME}} found for the given {{VONAME}}Id otherwise
			// throw exception
			{{VONAME}}Model {{VONAME_CAMELCASE}}Model = {{VONAME_CAMELCASE}}Repository.findBy{{VONAME}}Id({{VONAME_CAMELCASE}}Id);
			if (Objects.isNull({{VONAME_CAMELCASE}}Model)) {
				log.error(
						"{{VONAME}}ServiceImpl::get{{VONAME}}By{{VONAME}}Id:: {{VONAME}} not found for the given {{VONAME_CAMELCASE}}Id: {}!",
						{{VONAME_CAMELCASE}}Id);
				throw new {{VONAME}}NotFoundException({{BASE_ERROR_CODE}}+2, {{VONAME_CAMELCASE}}Id);
			}
			return MapperUtils.map{{VONAME}}({{VONAME_CAMELCASE}}Model);
		} catch ({{VONAME}}NotFoundException {{VONAME_CAMELCASE}}NotFoundException) {
			throw {{VONAME_CAMELCASE}}NotFoundException;
		} catch (Exception exception) {
			log.error(
					"{{VONAME}}ServiceImpl::get{{VONAME}}By{{VONAME}}Id:: Unable to retrieve {{VONAME}} for the given {{VONAME_CAMELCASE}}Id: {0}!",
					{{VONAME_CAMELCASE}}Id, exception);
			throw new {{VONAME}}Exception({{BASE_ERROR_CODE}}+3, {{VONAME_CAMELCASE}}Id);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void delete{{VONAME}}By{{VONAME}}Id(String {{VONAME_CAMELCASE}}Id)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException {
		try {
			// check if {{VONAME}} found for the given {{VONAME}}Id and status
			// is not DELETED otherwise throw exception
			{{VONAME}} {{VONAME_CAMELCASE}} = get{{VONAME}}By{{VONAME}}Id({{VONAME_CAMELCASE}}Id);
			if (Objects.isNull({{VONAME_CAMELCASE}}) || {{VONAME_CAMELCASE}}.getIsDeleted()) {
				log.error(
						"{{VONAME}}ServiceImpl::delete{{VONAME}}By{{VONAME}}Id:: {{VONAME}} already deleted for the given {{VONAME_CAMELCASE}}Id: {}!",
						{{VONAME_CAMELCASE}}Id);
				throw new {{VONAME}}NotFoundException({{BASE_ERROR_CODE}}+6, {{VONAME_CAMELCASE}}Id);
			}
			{{VONAME_CAMELCASE}}.setIsDeleted(true);
			{{VONAME_CAMELCASE}}Repository.save(MapperUtils.map{{VONAME}}Model({{VONAME_CAMELCASE}}));
		} catch ({{VONAME}}NotFoundException {{VONAME_CAMELCASE}}NotFoundException) {
			throw {{VONAME_CAMELCASE}}NotFoundException;
		} catch (Exception exception) {
			log.error(
					"{{VONAME}}ServiceImpl::delete{{VONAME}}By{{VONAME}}Id:: Unable to deleted {{VONAME}} for the given {{VONAME_CAMELCASE}}Id: {}!",
					{{VONAME_CAMELCASE}}Id, exception);
			throw new {{VONAME}}Exception({{BASE_ERROR_CODE}}+7, {{VONAME_CAMELCASE}}Id);
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<{{VONAME}}> get{{VONAME}}ListBy{{VONAME}}Ids(
			Fetch{{VONAME}}ListRequest fetch{{VONAME}}ListRequest)
			throws {{VONAME}}NotFoundException, {{VONAME}}Exception {
		List<String> requested{{VONAME}}Ids = new ArrayList<>();
		try {
			if (CollectionUtils.isEmpty(fetch{{VONAME}}ListRequest.get{{VONAME}}IdList())) {
				log.error(
						"{{VONAME}}ServiceImpl::get{{VONAME}}ListBy{{VONAME}}Ids:: {{VONAME_CAMELCASE}}Ids should be given!");
				throw new {{VONAME}}NotFoundException({{BASE_ERROR_CODE}}+8, "{{VONAME_CAMELCASE}}Ids should be given!");
			}
			requested{{VONAME}}Ids = fetch{{VONAME}}ListRequest.get{{VONAME}}IdList().stream().distinct()
					.collect(Collectors.toList());
			List<String> {{VONAME_CAMELCASE}}Ids = new ArrayList<>();
			List<{{VONAME}}Model> {{VONAME_CAMELCASE}}ModelList = {{VONAME_CAMELCASE}}Repository
					.findBy{{VONAME}}IdIn(requested{{VONAME}}Ids);
			if (CollectionUtils.isEmpty({{VONAME_CAMELCASE}}ModelList)) {
				log.error(
						"{{VONAME}}ServiceImpl::get{{VONAME}}ListBy{{VONAME}}Ids:: {{VONAME}}s not found for the given {{VONAME_CAMELCASE}}Ids: {}!",
						requested{{VONAME}}Ids.toString());
				throw new {{VONAME}}NotFoundException({{BASE_ERROR_CODE}}+9,
						"{{VONAME}}s not found for the given {{VONAME_CAMELCASE}}Ids!",
						requested{{VONAME}}Ids.toString());
			}

			if ({{VONAME_CAMELCASE}}ModelList.size() < requested{{VONAME}}Ids.size()) {
				for ({{VONAME}}Model {{VONAME_CAMELCASE}}Model : {{VONAME_CAMELCASE}}ModelList) {
					{{VONAME_CAMELCASE}}Ids.add({{VONAME_CAMELCASE}}Model.get{{VONAME}}Id());
				}
				requested{{VONAME}}Ids.removeAll({{VONAME_CAMELCASE}}Ids);
				log.error(
						"{{VONAME}}ServiceImpl::get{{VONAME}}ListBy{{VONAME}}Ids:: The following {{VONAME}}Ids: {} records are missing!",
						requested{{VONAME}}Ids.toString());
				throw new {{VONAME}}NotFoundException({{BASE_ERROR_CODE}}+10, requested{{VONAME}}Ids.toString());
			}
			return MapperUtils.map{{VONAME}}VOs({{VONAME_CAMELCASE}}ModelList);
		} catch ({{VONAME}}NotFoundException {{VONAME_CAMELCASE}}NotFoundException) {
			throw {{VONAME_CAMELCASE}}NotFoundException;
		} catch (Exception exception) {
			log.error(
					"{{VONAME}}ServiceImpl::get{{VONAME}}ByTaskStatusAnd{{VONAME}}Id:: Unable to retrieve {{VONAME}} for the given {{VONAME_CAMELCASE}}Ids: {}!",
					requested{{VONAME}}Ids.toString(), exception);
			throw new {{VONAME}}Exception({{BASE_ERROR_CODE}}+11, requested{{VONAME}}Ids.toString());
		}
	}

}