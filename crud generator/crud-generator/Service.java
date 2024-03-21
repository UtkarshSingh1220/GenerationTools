package {{ROOT_PACKAGE}}.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import {{ROOT_PACKAGE}}.exception.{{VONAME}}Exception;
import {{ROOT_PACKAGE}}.exception.{{VONAME}}NotFoundException;
import {{ROOT_PACKAGE}}.model.{{VONAME}}Model;
import {{ROOT_PACKAGE}}.repository.{{VONAME}}Repository;
import {{ROOT_PACKAGE}}.util.MapperUtils;
import {{ROOT_PACKAGE}}.vo.Fetch{{VONAME}}ListRequest;
import {{ROOT_PACKAGE}}.vo.Search{{VONAME}}Request;
import {{ROOT_PACKAGE}}.vo.{{VONAME}};


/**
 * The {{VONAME}} business logic interface
 * 
 * @author {{AUTHOR}}
 */

public interface {{VONAME}}Service {

	/**
	 * This method is used to create and update for {{VONAME}}.
	 * 
	 * @param {{VONAME_CAMELCASE}}
	 * @return {{VONAME}}
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public {{VONAME}} save{{VONAME}}({{VONAME}} {{VONAME_CAMELCASE}})
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

	/**
	 * This method is used to get {{VONAME}} by {{VONAME}}NameSearchable or
	 * get All {{VONAME}}.
	 * 
	 * @param search{{VONAME}}Request
	 * @param pageable
	 * @return
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public Page<{{VONAME}}> get{{VONAME}}ByParameters(Search{{VONAME}}Request search{{VONAME}}Request,
			Pageable pageable) throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

	/**
	 * This method is used to get {{VONAME}} by {{VONAME}}Id.
	 * 
	 * @param {{VONAME_CAMELCASE}}Id
	 * @return {{VONAME}}
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public {{VONAME}} get{{VONAME}}By{{VONAME}}Id(String {{VONAME_CAMELCASE}}Id)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

	/**
	 * This method is used to delete {{VONAME}} by {{VONAME}}Id.
	 * 
	 * @param {{VONAME_CAMELCASE}}Id
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public void delete{{VONAME}}By{{VONAME}}Id(String {{VONAME_CAMELCASE}}Id)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

	/**
	 * This method is used to get {{VONAME}}List By {{VONAME}}Ids
	 * 
	 * @param fetch{{VONAME}}ListRequest
	 * @return List<{{VONAME}}>
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public List<{{VONAME}}> get{{VONAME}}ListBy{{VONAME}}Ids(
			Fetch{{VONAME}}ListRequest fetch{{VONAME}}ListRequest)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

}
