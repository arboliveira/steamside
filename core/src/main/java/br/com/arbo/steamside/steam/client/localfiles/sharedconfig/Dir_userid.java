package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;

import javax.inject.Inject;

public class Dir_userid {

	@Inject
	public Dir_userid(final Dir_userdata dir_userdata) {
		this.dir_userdata = dir_userdata;
	}

	private final Dir_userdata dir_userdata;

	public File userid() {
		final File userdata = dir_userdata.userdata();
		return detect_userid(userdata);
	}

	private static File detect_userid(final File userdata) {
		final File[] files = userdata.listFiles();
		if (files.length == 1) return files[0];
		for (File file : files)
			if (file.getName().equals("STEAM_USERID_DETECTED")) return file;
		throw new RuntimeException("More than 1 user: to be implemented");
	}
}
