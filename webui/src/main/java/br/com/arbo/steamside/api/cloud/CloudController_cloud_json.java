package br.com.arbo.steamside.api.cloud;

import java.nio.file.Paths;

import javax.inject.Inject;

import br.com.arbo.steamside.cloud.CloudSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsPersistence;
import br.com.arbo.steamside.settings.local.SteamsideLocalXml;

public class CloudController_cloud_json implements CloudController_cloud
{

	@Inject
	public CloudController_cloud_json(
		CloudSettingsFactory cloud,
		LocalSettingsFactory localSettingsFactory,
		LocalSettingsPersistence persistence)
	{
		this.cloud = cloud;
		this.localSettingsFactory = localSettingsFactory;
		this.persistence = persistence;
	}

	@Override
	public CloudDTO jsonable()
	{
		CloudDTO dto = new CloudDTO();
		dto.cloud = cloud();
		dto.cloudSyncedLocation_hydrate(localSettingsFactory);
		return dto;
	}

	@Override
	public CloudDTO post(CloudDTO payload)
	{
		SteamsideLocalXml xml = new SteamsideLocalXml();
		xml.cloud = payload.cloud;
		xml.cloudSyncedLocation(Paths.get(payload.cloudSyncedLocation));
		persistence.write(xml);
		return payload;
	}

	private boolean cloud()
	{
		try
		{
			return cloud.read().isEnabled();
		}
		catch (br.com.arbo.steamside.cloud.CloudSettingsFactory.Missing e)
		{
			return false;
		}
	}

	private final LocalSettingsPersistence persistence;

	CloudSettingsFactory cloud;
	LocalSettingsFactory localSettingsFactory;

}
