package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;

public class File_localconfig_vdf {

	public static File localconfig_vdf() {
		final File userid = Dir_userid.userid();
		final File useridconfig = new File(userid, "/config");
		return new File(useridconfig, "localconfig.vdf");
	}

}
