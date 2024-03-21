package {{ROOT_PACKAGE}}.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import {{ROOT_PACKAGE}}.exception.{{VONAME}}Exception;
import {{ROOT_PACKAGE}}.exception.{{VONAME}}NotFoundException;
import {{ROOT_PACKAGE}}.vo.Fetch{{VONAME}}ListRequest;
import {{ROOT_PACKAGE}}.vo.Search{{VONAME}}Request;
import {{ROOT_PACKAGE}}.vo.{{VONAME}};

/**
 * This interface exposes the endpoints related to the {{VONAME}}.
 * 
 * @author utkarsh
 */
public interface {{VONAME}}Controller {

	/**
	 * This API is used to create and update for {{VONAME}}.
	 * 
	 * @param {{VONAME}}
	 * @return {{VONAME}}
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public ResponseEntity<{{VONAME}}> save{{VONAME}}({{VONAME}} {{VONAME}})
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

	/**
	 * This API is used to get {{VONAME}} by {{VONAME}}NameSearchable or get
	 * All {{VONAME}}.
	 * 
	 * @param search{{VONAME}}Request
	 * @param pageable
	 * @return Page<{{VONAME}}>
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public ResponseEntity<Page<{{VONAME}}>> get{{VONAME}}ByParameters(
			Search{{VONAME}}Request search{{VONAME}}Request, Pageable pageable)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

	/**
	 * This API is used to get {{VONAME}} by {{VONAME_CAMELCASE}}Id.
	 * 
	 * @param {{VONAME_CAMELCASE}}Id
	 * @return {{VONAME}}
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public ResponseEntity<{{VONAME}}> get{{VONAME}}By{{VONAME}}Id(String {{VONAME_CAMELCASE}}Id)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

	/**
	 * This API is used to delete {{VONAME}} by {{VONAME_CAMELCASE}}Id.
	 * 
	 * @param {{VONAME_CAMELCASE}}Id
	 * @return
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public ResponseEntity<Void> delete{{VONAME}}By{{VONAME}}Id(String {{VONAME_CAMELCASE}}Id)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

	/**
	 * This API is used to get {{VONAME}}List By {{VONAME}}Ids
	 * 
	 * @param fetch{{VONAME}}ListRequest
	 * @return List<{{VONAME}}>
	 * @throws {{VONAME}}Exception
	 * @throws {{VONAME}}NotFoundException
	 */
	public ResponseEntity<List<{{VONAME}}>> get{{VONAME}}ListBy{{VONAME}}Ids(
			Fetch{{VONAME}}ListRequest fetch{{VONAME}}ListRequest)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException;

}