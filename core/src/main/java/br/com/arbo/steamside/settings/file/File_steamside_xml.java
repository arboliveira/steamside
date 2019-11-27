package br.com.arbo.steamside.settings.file;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;

public class File_steamside_xml
{

	@Inject
	public File_steamside_xml(Dir_userid dir_userid)
	{
		this.dir_userid = dir_userid;
	}

	public File steamside_xml()
	{
		final File userid = dir_userid.userid();
		final File userid_Steamside_remote =
			new File(userid, "/Steamside/steamside-data");
		return new File(userid_Steamside_remote, "steamside.xml");
	}

	private final Dir_userid dir_userid;

}
