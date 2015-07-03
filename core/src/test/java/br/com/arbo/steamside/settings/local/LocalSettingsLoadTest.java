package br.com.arbo.steamside.settings.local;

import java.io.File;

import org.junit.Test;

public class LocalSettingsLoadTest
{

	@SuppressWarnings("static-method")
	@Test(expected = br.com.arbo.steamside.settings.local.LocalSettingsFactory.Missing.class)
	public void missing()
	{
		File_steamside_local_xml_Supplier bogus =
			() -> {
				return new File("DOES_NOT_EXIST");
			};
		new LocalSettingsLoad(bogus).read();
	}

}
