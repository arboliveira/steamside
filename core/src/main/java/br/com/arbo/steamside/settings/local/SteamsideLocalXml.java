package br.com.arbo.steamside.settings.local;

import java.nio.file.Path;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "steamside-local")
public class SteamsideLocalXml
{

	public void cloudSyncedLocation(Path path)
	{
		cloudSyncedLocation = path.toString();
	}

	public boolean cloud;

	public String cloudSyncedLocation;

}
