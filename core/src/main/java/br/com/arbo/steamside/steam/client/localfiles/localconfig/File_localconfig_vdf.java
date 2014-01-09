package br.com.arbo.steamside.steam.client.localfiles.localconfig;

import java.io.File;

import javax.inject.Inject;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Dir_userid;

public class File_localconfig_vdf {

	private final Dir_userid dir_userid;

	@Inject
	public File_localconfig_vdf(final Dir_userid dir_userid) {
		super();
		this.dir_userid = dir_userid;
	}

	public File localconfig_vdf() {
		final File userid = dir_userid.userid();
		final File useridconfig = new File(userid, "/config");
		return new File(useridconfig, "localconfig.vdf");
	}

}
