package {{ROOT_PACKAGE}}.util;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import {{ROOT_PACKAGE}}.model.{{VONAME}}Model;
import {{ROOT_PACKAGE}}.vo.{{VONAME}};

@Mapper(componentModel = "spring")
public interface {{VONAME}}Mapper {

	@Mapping(source = "createdTime", target = "createdOn", qualifiedByName = "createdTimeToCreatedOn")
	@Mapping(source = "lastModifiedTime", target = "lastModifiedOn", qualifiedByName = "createdTimeToCreatedOn")
	public {{VONAME}}Model map{{VONAME}}Model({{VONAME}} {{VONAME_CAMELCASE}});

	@Mapping(source = "createdOn", target = "createdTime", qualifiedByName = "createdOnTocreatedTime")
	@Mapping(source = "lastModifiedOn", target = "lastModifiedTime", qualifiedByName = "createdOnTocreatedTime")
	public {{VONAME}} map{{VONAME}}({{VONAME}}Model {{VONAME_CAMELCASE}}Model);

	@Named("createdOnTocreatedTime")
	public static Instant mapInstantToZoneDateTimeCreatedOnToCreatedTime(Instant createdOn) {
		if (createdOn == null) {
			return null;
		}
		return createdOn;
	}

	@Named("createdTimeToCreatedOn")
	public static Instant mapInstantToZoneDateTimeCreatedTimeToCreatedOn(Instant createdTime) {
		if (createdTime == null) {
			return null;
		}
		return createdTime;
	}
}
