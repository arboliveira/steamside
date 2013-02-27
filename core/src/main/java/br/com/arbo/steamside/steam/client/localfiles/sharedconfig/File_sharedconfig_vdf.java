package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

public class File_sharedconfig_vdf {

	public static File sharedconfig_vdf() {
		final File userdata = UserdataDirectory.userdata();
		final File userid = detect_userid(userdata);
		final File userid7remote = new File(userid, "/7/remote");
		return new File(userid7remote, "sharedconfig.vdf");
	}

	private static File detect_userid(final File userdata) {
		final File[] files = userdata.listFiles();
		if (files.length == 1) return files[0];
		throw new RuntimeException("More than 1 user: to be implemented");
	}

}
