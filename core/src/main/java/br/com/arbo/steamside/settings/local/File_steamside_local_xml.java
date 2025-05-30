package br.com.arbo.steamside.settings.local;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;

public class File_steamside_local_xml
	implements File_steamside_local_xml_Supplier
{

	@Inject
	public File_steamside_local_xml(Dir_userid dir_userid)
	{
		this.dir_userid = dir_userid;
	}

	@Override
	public File steamside_local_xml()
	{
		final File userid = dir_userid.userid();
		final File userid_Steamside_local =
			new File(userid, "/Steamside/steamside-local-settings");
		return new File(userid_Steamside_local, "steamside-local-settings.xml");
	}

	private final Dir_userid dir_userid;

}
