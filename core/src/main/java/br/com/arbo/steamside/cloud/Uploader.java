package br.com.arbo.steamside.cloud;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

public class Uploader {

	private static String read(File file)
	{
		try
		{
			return FileUtils.readFileToString(file, "UTF-8");
		}
		catch (IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	@Inject
	public Uploader(Cloud cloud, CloudSettings settings)
	{
		this.cloud = cloud;
		this.settings = settings;
	}

	public void upload(File file)
	{
		if (!this.settings.isEnabled()) return;

		String content = read(file);
		cloud.upload(content);
	}

	private final Cloud cloud;

	private final CloudSettings settings;
}
