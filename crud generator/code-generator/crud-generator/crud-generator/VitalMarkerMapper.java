package com.karkinos.mdm.admin.util;

import java.time.Instant;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.karkinos.mdm.admin.model.VitalMarkerModel;
import com.karkinos.mdm.admin.vo.VitalMarker;

@Mapper(componentModel = "spring")
public interface VitalMarkerMapper {

	@Mapping(source = "createdTime", target = "createdOn", qualifiedByName = "createdTimeToCreatedOn")
	@Mapping(source = "lastModifiedTime", target = "lastModifiedOn", qualifiedByName = "createdTimeToCreatedOn")
	public VitalMarkerModel mapVitalMarkerModel(VitalMarker vitalMarker);

	@Mapping(source = "createdOn", target = "createdTime", qualifiedByName = "createdOnTocreatedTime")
	@Mapping(source = "lastModifiedOn", target = "lastModifiedTime", qualifiedByName = "createdOnTocreatedTime")
	public VitalMarker mapVitalMarker(VitalMarkerModel vitalMarkerModel);

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
