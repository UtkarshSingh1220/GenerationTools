package {{ROOT_PACKAGE}}.controller;

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

import {{ROOT_PACKAGE}}.exception.{{VONAME}}Exception;
import {{ROOT_PACKAGE}}.exception.{{VONAME}}NotFoundException;
import {{ROOT_PACKAGE}}.service.{{VONAME}}Service;
import {{ROOT_PACKAGE}}.vo.Fetch{{VONAME}}ListRequest;
import {{ROOT_PACKAGE}}.vo.Search{{VONAME}}Request;
import {{ROOT_PACKAGE}}.vo.{{VONAME}};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Provides the implementation for create {{VONAME}} related endpoints
 */
@RestController
@RequestMapping("{{CLASS_REQUEST_MAPPING}}")
@Api(value = "{{VONAME}} APIs")
@RefreshScope
public class {{VONAME}}ControllerImpl implements {{VONAME}}Controller {

	@Autowired
	{{VONAME}}Service {{VONAME_CAMELCASE}}Service;

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to create/update {{VONAME}}.", response = {{VONAME}}.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Successfully created"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+2, message = "{{VONAME}} not found for the given {{VONAME_CAMELCASE}}Id!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+3, message = "Unable to retrieve {{VONAME}} for the given {{VONAME_CAMELCASE}}Id!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+8, message = "{{VONAME}} found for the generated {{VONAME_CAMELCASE}}Id!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+12, message = "{{VONAME}} already exists for the given {{VONAME_CAMELCASE}}Name!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+8, message = "{{VONAME}} cannot be created with isDeleted as true!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+1, message = "Unable to create/update {{VONAME}}") })
	@Override
	@PutMapping(value = "/")
	public ResponseEntity<{{VONAME}}> save{{VONAME}}(@RequestBody @Valid {{VONAME}} {{VONAME_CAMELCASE}})
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException {
		return new ResponseEntity<>({{VONAME_CAMELCASE}}Service.save{{VONAME}}({{VONAME_CAMELCASE}}), HttpStatus.CREATED);
	}
65879

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to retrieve {{VONAME}} details by Parameters.", response = Page.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+4, message = "No {{VONAME}} found for the given search criteria"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+5, message = "Unable to retrieve {{VONAME}} for the given parameters") })
	@Override
	@PostMapping(value = "/fetch-by-parameters")
	public ResponseEntity<Page<{{VONAME}}>> get{{VONAME}}ByParameters(
			@RequestBody @Valid Search{{VONAME}}Request search{{VONAME}}Request, Pageable pageable)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException {
		return new ResponseEntity<>(
				{{VONAME_CAMELCASE}}Service.get{{VONAME}}ByParameters(search{{VONAME}}Request, pageable), HttpStatus.OK);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to get {{VONAME}} by {{VONAME_CAMELCASE}}Id.", response = Void.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+2, message = "{{VONAME}} not found for the given {{VONAME_CAMELCASE}}Id!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+3, message = "Unable to retrieve {{VONAME}} for the given {{VONAME_CAMELCASE}}Id!") })
	@Override
	@GetMapping(value = "/{{{VONAME_CAMELCASE}}Id}")
	public ResponseEntity<{{VONAME}}> get{{VONAME}}By{{VONAME}}Id(
			@PathVariable(value = "{{VONAME_CAMELCASE}}Id", required = true) String {{VONAME_CAMELCASE}}Id)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException {

		return new ResponseEntity<>({{VONAME_CAMELCASE}}Service.get{{VONAME}}By{{VONAME}}Id({{VONAME_CAMELCASE}}Id),
				HttpStatus.OK);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to delete {{VONAME}} by {{VONAME_CAMELCASE}}Id.", response = Void.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Successfully deleted"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+2, message = "{{VONAME}} not found for the given {{VONAME_CAMELCASE}}Id!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+3, message = "Unable to retrieve {{VONAME}} for the given {{VONAME_CAMELCASE}}Id!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+6, message = "{{VONAME}} already deleted for the given {{VONAME_CAMELCASE}}Id!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+7, message = "Unable to deleted {{VONAME}} for the given {{VONAME_CAMELCASE}}Id!") })
	@Override
	@DeleteMapping(value = "/{{{VONAME_CAMELCASE}}Id}")
	public ResponseEntity<Void> delete{{VONAME}}By{{VONAME}}Id(
			@PathVariable(value = "{{VONAME_CAMELCASE}}Id", required = true) String {{VONAME_CAMELCASE}}Id)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException {
		{{VONAME_CAMELCASE}}Service.delete{{VONAME}}By{{VONAME}}Id({{VONAME_CAMELCASE}}Id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * {@inheritDoc}
	 */
	@ApiOperation(value = "This API is used to get {{VONAME}}List by {{VONAME_CAMELCASE}}Ids.", response = List.class)
	@ApiImplicitParams(@ApiImplicitParam(name = "Content-Type", value = "application/json", required = true, allowEmptyValue = false, paramType = "header"))
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+8, message = "{{VONAME_CAMELCASE}}Ids should be given!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+9, message = "{{VONAME}}s not found for the given {{VONAME_CAMELCASE}}Ids!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+10, message = "The following {{VONAME}}Ids records are missing!"),
			@ApiResponse(code = {{BASE_ERROR_CODE}}+11, message = "Unable to retrieve {{VONAME}} for the given {{VONAME_CAMELCASE}}Ids!") })
	@Override
	@PostMapping(value = "/fetch-by-id-list")
	public ResponseEntity<List<{{VONAME}}>> get{{VONAME}}ListBy{{VONAME}}Ids(
			@RequestBody @Valid Fetch{{VONAME}}ListRequest fetch{{VONAME}}ListRequest)
			throws {{VONAME}}Exception, {{VONAME}}NotFoundException {
		return new ResponseEntity<>(
				{{VONAME_CAMELCASE}}Service.get{{VONAME}}ListBy{{VONAME}}Ids(fetch{{VONAME}}ListRequest),
				HttpStatus.OK);
	}
}