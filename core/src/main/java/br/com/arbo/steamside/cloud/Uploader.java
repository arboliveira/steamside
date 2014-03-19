package br.com.arbo.steamside.cloud;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Uploader {

	private static String read(File file) {
		try {
			return FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void upload(File file) {
		String content = read(file);
		upload(content);
	}

	@SuppressWarnings("static-method")
	public void upload(String content) {
		new Dontpad().post(content);
	}
}
