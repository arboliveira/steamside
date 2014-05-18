package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

import javax.inject.Inject;

public class File_sharedconfig_vdf {

	private final Dir_userid dir_userid;

	@Inject
	public File_sharedconfig_vdf(Dir_userid dir_userid) {
		this.dir_userid = dir_userid;
	}

	public File sharedconfig_vdf() {
		final File userid = dir_userid.userid();
		final File userid7remote = new File(userid, "/7/remote");
		return new File(userid7remote, "sharedconfig.vdf");
	}

}
