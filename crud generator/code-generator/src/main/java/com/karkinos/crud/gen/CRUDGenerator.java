package com.karkinos.crud.gen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.lang3.StringUtils;

public class CRUDGenerator {
	// java templates
	private static final String CONTROLLER = "Controller.java";
	private static final String CONTROLLER_IMPL = "ControllerImpl.java";
	private static final String SERVICE = "Service.java";
	private static final String SERVICE_IMPL = "ServiceImpl.java";
	private static final String EXCEPTION = "Exception.java";
	private static final String NOT_FOUND_EXCEPTION = "NotFoundException.java";
	private static final String MAPPER = "Mapper.java";
	private static final String MONGO_REPOSITORY = "MongoRepository.java";
	private static final String PG_REPOSITORY = "PGRepository.java";

	// partial text templates
	private static final String TEMPLATE_ERROR_UTILS = "ErrorUtilsTemplate.txt";
	private static final String TEMPLATE_EXCEPTION_HANDLER = "ExceptionHandlerTemplate.txt";
	private static final String TEMPLATE_MAPPER_UTILS = "MapperUtilsTemplate.txt";

	// placeholders to be replaced in the templates
	private static final String PLACEHOLDER_VONAME = "{{VONAME}}";
	private static final String PLACEHOLDER_ROOT_PACKAGE = "{{ROOT_PACKAGE}}";
	private static final String PLACEHOLDER_AUTHOR = "{{AUTHOR}}";
	private static final String PLACEHOLDER_VONAME_CAMELCASE = "{{VONAME_CAMELCASE}}";
	private static final String PLACEHOLDER_VONAME_LOWERCASE = "{{VONAME_LOWERCASE}}";
	private static final String PLACEHOLDER_CLASS_REQUEST_MAPPING = "{{CLASS_REQUEST_MAPPING}}";
	private static final String PLACEHOLDER_BASE_ERROR_CODE = "{{BASE_ERROR_CODE}}";
	private static final String PLACEHOLDER_SERIAL_VERSION_ID = "{{SERIAL_VERSION_ID}}";

	private static String[] JavaTemplates = { CONTROLLER, CONTROLLER_IMPL, SERVICE, SERVICE_IMPL, EXCEPTION,
			NOT_FOUND_EXCEPTION, MAPPER, MONGO_REPOSITORY, PG_REPOSITORY };

	private static String[] TextTemplates = { TEMPLATE_ERROR_UTILS, TEMPLATE_EXCEPTION_HANDLER, TEMPLATE_MAPPER_UTILS };

	public static void main(String[] args) throws Exception {
		boolean acceptInputs = false;

		String dbType, voName, rootPackage, author, classRequestMapping;
		int baseErrorCode = 0;

		if (!acceptInputs) {
			dbType = "PG";
			voName = "VitalMarker";
			rootPackage = "com.karkinos.mdm.admin";
			author = "utkarsh singh";
			classRequestMapping = "/vital-marker";
			baseErrorCode = 56300;
		} else {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.print("dbType:");
			dbType = br.readLine();
			System.out.println("");

			System.out.print("voName:");
			voName = br.readLine();
			System.out.println("");

			System.out.print("rootPackage:");
			rootPackage = br.readLine();
			System.out.println("");

			System.out.print("author:");
			author = br.readLine();
			System.out.println("");

			System.out.print("classRequestMapping:");
			classRequestMapping = br.readLine();
			System.out.println("");

			System.out.print("baseErrorCode:");
			baseErrorCode = Integer.parseInt(br.readLine());
			System.out.println("");

			br.close();
		} 

		String voNameCamelCase = toCamelCase(voName);
		String voNameLowerCase = toLowerCase(voName);

		// loop thru the java templates, read them,
		// replace the placeholders,
		// create output files with the VOName+filename into the output folder
		for (String javaTemplate : JavaTemplates) {
			if (("PG".equals(dbType) && MONGO_REPOSITORY.equals(javaTemplate))
					|| ("MONGO".equals(dbType) && PG_REPOSITORY.equals(javaTemplate))) {
				continue;
			}
			String data = FileHandler.readFrameworkFile(javaTemplate);
			data = data.replace(PLACEHOLDER_VONAME, voName).replace(PLACEHOLDER_ROOT_PACKAGE, rootPackage)
					.replace(PLACEHOLDER_AUTHOR, author).replace(PLACEHOLDER_VONAME_CAMELCASE, voNameCamelCase)
					.replace(PLACEHOLDER_VONAME_LOWERCASE, voNameLowerCase)
					.replace(PLACEHOLDER_CLASS_REQUEST_MAPPING, classRequestMapping)
					.replace(PLACEHOLDER_SERIAL_VERSION_ID, getSerialVersionId());
			data = handleErrorCodes(data, baseErrorCode, false);

			String fname = voName + (javaTemplate.endsWith("Repository.java") ? "Repository.java" : javaTemplate);
			FileHandler.writeFrameworkOutputFile(fname, data);
		}

		// loop thru the text templates,
		// replace the placeholders,
		// create the output files with the same filename into the output folder
		for (String textTemplate : TextTemplates) {
			String data = FileHandler.readFrameworkFile(textTemplate);
			data = data.replace(PLACEHOLDER_VONAME, voName).replace(PLACEHOLDER_ROOT_PACKAGE, rootPackage)
					.replace(PLACEHOLDER_AUTHOR, author).replace(PLACEHOLDER_VONAME_CAMELCASE, voNameCamelCase)
					.replace(PLACEHOLDER_VONAME_LOWERCASE, voNameLowerCase)
					.replace(PLACEHOLDER_CLASS_REQUEST_MAPPING, classRequestMapping)
					.replace(PLACEHOLDER_SERIAL_VERSION_ID, getSerialVersionId());
			boolean isErrorUtils = false;
			if ("ErrorUtilsTemplate.txt".equals(textTemplate)) {
				isErrorUtils = true;
			}
			data = handleErrorCodes(data, baseErrorCode, isErrorUtils);

			String fname = textTemplate;
			FileHandler.writeFrameworkOutputFile(fname, data);
		}

		System.out.println("done");
	}

	public static String toLowerCase(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}

		return str.toLowerCase();
	}

	public static String toCamelCase(String str) {
		if (StringUtils.isBlank(str)) {
			return str;
		}

		if (str.length() > 1) {
			return (str.charAt(0) + "").toLowerCase() + str.substring(1);
		}
		return (str.charAt(0) + "").toLowerCase();
	}

	public static String getSerialVersionId() {
		return System.currentTimeMillis() + "";
	}

	public static String handleErrorCodes(String data, int baseErrorCode, boolean isErrorUtils) {
		int ind1 = -1;
		int ind2 = -1;
		String replaceStr = null;
		while (true) {
			replaceStr = PLACEHOLDER_BASE_ERROR_CODE;
			ind1 = data.indexOf(PLACEHOLDER_BASE_ERROR_CODE);

			if (ind1 == -1) {
				return data;
			}
			ind2 = data.indexOf("+", ind1 + 1);

			if (isErrorUtils) {
				// In ErrorUtils {{BASE_ERROR_CODE}}+8L,
				ind1 = data.indexOf("L,", ind2 + 1);
			} else {
				// others {{BASE_ERROR_CODE}}+8,
				ind1 = data.indexOf(",", ind2 + 1);
			}

			String incrementStr = data.substring(ind2 + 1, ind1);
			int errorCode = baseErrorCode + Integer.parseInt(incrementStr);
//			System.out.println("errorCode: " + errorCode);
			String errorCodeStr = errorCode + "";

			replaceStr += "+" + incrementStr;
			if (isErrorUtils) {
				replaceStr += "L";
				errorCodeStr += "L";
			}
			replaceStr += ",";
//			System.out.println("replaceStr: " + replaceStr);

			data = data.replace(replaceStr, errorCodeStr + ",");

			// System.out.println(data);
		}
	}
}
