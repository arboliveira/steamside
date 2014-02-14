package br.com.arbo.steamside.continues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.library.Library;

public class ContinuesFromSteamClientLocalfiles implements ContinuesRooster {

	@NonNull
	private final FilterContinues continues;
	private final Library library;

	@Override
	public void accept(final AppVisitor visitor) {
		final List<App> list = queryApps();
		sort(list);
		for (final App app : list)
			visitor.each(app);
	}

	private List<App> queryApps() {
		final List<App> list = new ArrayList<App>();
		from_accept(new AppVisitor()/* @formatter:off */ {	@Override public void each(final App app) /* @formatter:on */
			{
				list.add(app);
			}
		});
		return list;
	}

	@SuppressWarnings("null")
	private void from_accept(final AppVisitor add) {
		library.accept(continues, add);
	}

	private static void sort(final List<App> list) {
		Collections.sort(list, new LastPlayedDescending());
		// TODO Prioritize games launched by current user
	}

	@Inject
	public ContinuesFromSteamClientLocalfiles(
			final @NonNull FilterContinues continues,
			final Library library) {
		this.library = library;
		this.continues = continues;
	}

}
