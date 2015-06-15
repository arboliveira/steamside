package br.com.arbo.steamside.api.cloud;

import javax.inject.Inject;

import br.com.arbo.steamside.cloud.CloudSettingsFactory;
import br.com.arbo.steamside.cloud.dontpad.DontpadAddress;
import br.com.arbo.steamside.cloud.dontpad.DontpadSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsPersistence;
import br.com.arbo.steamside.settings.local.SteamsideLocalXml;

public class CloudController_cloud_json implements CloudController_cloud
{

	@Inject
	public CloudController_cloud_json(
		CloudSettingsFactory cloud,
		DontpadSettingsFactory dontpad,
		LocalSettingsPersistence persistence)
	{
		this.cloud = cloud;
		this.dontpad = dontpad;
		this.persistence = persistence;
	}

	@Override
	public CloudDTO jsonable()
	{
		CloudDTO dto = new CloudDTO();
		dto.cloud = cloud();
		dto.dontpad(dontpad);
		return dto;
	}

	@Override
	public CloudDTO post(CloudDTO payload)
	{
		SteamsideLocalXml xml = new SteamsideLocalXml();
		xml.cloud = payload.cloud;
		xml.dontpad(new DontpadAddress(payload.dontpad));
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
	DontpadSettingsFactory dontpad;

}
