package br.com.arbo.steamside.cloud;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

public class Uploader {

	private static String read(File file)
	{
		try {
			return FileUtils.readFileToString(file, "UTF-8");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	public Uploader(Cloud cloud) {
		this.cloud = cloud;
	}

	public void upload(File file)
	{
		String content = read(file);
		upload(content);
	}

	@SuppressWarnings("static-method")
	public void upload(String content)
	{
		cloud.upload(content);
	}

	private final Cloud cloud;
}
