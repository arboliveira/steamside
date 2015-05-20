package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import java.io.File;
import java.util.Optional;

import javax.inject.Inject;

import com.google.common.base.Preconditions;

public class Dir_userid {

	private static void checkExists(final File[] files)
	{
		Preconditions.checkNotNull(files,
			"userdata does not exist?!");
	}

	private static void checkNotEmpty(final File[] files)
	{
		Preconditions.checkState(
			files.length > 0,
			"I've never seen an empty userdata before");
	}

	private static File detect_userid(final File userdata)
	{
		final File[] files = userdata.listFiles();

		checkExists(files);
		checkNotEmpty(files);

		if (files.length == 1)
			return files[0];

		File withMostGames = files[0];
		int max = gamesIn(withMostGames);

		for (int i = 1; i < files.length; i++)
		{
			File one = files[i];
			int n = gamesIn(one);
			if (n > max)
			{
				max = n;
				withMostGames = one;
			}
		}

		return withMostGames;
	}

	private static int gamesIn(File dir)
	{
		return Optional.ofNullable(dir.listFiles())
			.map(a -> a.length)
			.orElse(-1);
	}

	@Inject
	public Dir_userid(final Dir_userdata dir_userdata)
	{
		this.userid = detect_userid(dir_userdata.userdata());
	}

	public File userid()
	{
		return userid;
	}

	private final File userid;
}
