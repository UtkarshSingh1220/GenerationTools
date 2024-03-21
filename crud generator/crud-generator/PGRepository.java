package {{ROOT_PACKAGE}}.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import {{ROOT_PACKAGE}}.pg.model.{{VONAME}}Model;


@Repository
public interface {{VONAME}}Repository extends JpaRepository<{{VONAME}}Model, String> {
	{{VONAME}}Model findBy{{VONAME}}Id(String {{VONAME_CAMELCASE}}Id);

	List<{{VONAME}}Model> findBy{{VONAME}}IdIn(List<String> {{VONAME_CAMELCASE}}IdList);

	{{VONAME}}Model findBy{{VONAME}}Name(String {{VONAME_CAMELCASE}}Name);

}
