package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

public class Dir_userid {

	public static File userid() {
		final File userdata = Dir_userdata.userdata();
		return detect_userid(userdata);
	}

	private static File detect_userid(final File userdata) {
		final File[] files = userdata.listFiles();
		if (files.length == 1) return files[0];
		throw new RuntimeException("More than 1 user: to be implemented");
	}
}
