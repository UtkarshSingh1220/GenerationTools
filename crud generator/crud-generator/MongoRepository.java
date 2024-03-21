package {{ROOT_PACKAGE}}.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import {{ROOT_PACKAGE}}.model.{{VONAME}}Model;

@Repository
public interface {{VONAME}}Repository extends MongoRepository<{{VONAME}}Model, String> {

	{{VONAME}}Model findBy{{VONAME}}Id(String {{VONAME_CAMELCASE}}Id);

	List<{{VONAME}}Model> findBy{{VONAME}}IdIn(List<String> {{VONAME_CAMELCASE}}IdList);

	{{VONAME}}Model findBy{{VONAME}}Name(String {{VONAME_CAMELCASE}}Name);

}