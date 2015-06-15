package br.com.arbo.steamside.api.cloud;

import org.apache.commons.lang3.RandomStringUtils;

import br.com.arbo.steamside.cloud.dontpad.DontpadSettingsFactory;
import br.com.arbo.steamside.cloud.dontpad.DontpadSettingsFactory.Missing;

public class CloudDTO
{

	private static String address(DontpadSettingsFactory dontpad)
	{
		try
		{
			return dontpad.read().address().url();
		}
		catch (Missing e)
		{
			return "http://dontpad.com/"
				+ RandomStringUtils.randomAlphanumeric(5).toLowerCase();
		}
	}

	public void dontpad(DontpadSettingsFactory d)
	{
		this.dontpad = address(d);
	}

	public boolean cloud;

	public String dontpad;

}
