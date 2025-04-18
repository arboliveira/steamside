package br.com.arbo.steamside.cloud;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import org.apache.commons.io.FileUtils;

import br.com.arbo.steamside.cloud.CloudSettingsFactory.Missing;

public class Uploader
{

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
	public Uploader(CopySteamsideXmlToCloud cloud,
		CloudSettingsFactory settings)
	{
		this.cloud = cloud;
		this.settingsFactory = settings;
	}

	public void upload(File file) throws Missing
	{
		final CloudSettings read = this.settingsFactory.read();

		if (!read.isEnabled()) return;

		String content = read(file);
		cloud.copy(content);
	}

	private final CopySteamsideXmlToCloud cloud;

	private final CloudSettingsFactory settingsFactory;
}
