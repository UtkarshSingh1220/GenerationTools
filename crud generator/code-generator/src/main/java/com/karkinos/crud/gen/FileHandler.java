package com.karkinos.crud.gen;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileHandler {
	public static final String TEMPLATE_FOLDER = "C:\\KDS 2.0\\kare-tools\\code-generator\\crud-generator";
	public static final String OUTPUT_FOLDER = TEMPLATE_FOLDER + "\\crud-generator";

	// File handler
	public static String readFrameworkFile(String fname) throws IOException {
		return readFile(TEMPLATE_FOLDER, fname);
	}

	public static void writeFrameworkOutputFile(String fname, String data) throws IOException {
		writeFile(OUTPUT_FOLDER, fname, data);
	}

	public static String readFile(String path, String fname) throws IOException {
		return FileUtils.readFileToString(new File(path + "\\" + fname));
	}

	public static void writeFile(String path, String fname, String data) throws IOException {
		FileUtils.writeStringToFile(new File(path + "\\" + fname), data);
	}
}
