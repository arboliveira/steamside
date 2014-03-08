package br.com.arbo.steamside.continues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.library.Library;

public class ContinuesFromSteamClientLocalfiles implements ContinuesRooster {

	@NonNull
	private final FilterContinues continues;
	private final Library library;

	@Override
	public void accept(final Consumer<App> visitor) {
		final List<App> list = queryApps();
		sort(list);
		list.forEach(visitor);
	}

	private List<App> queryApps() {
		final List<App> list = new ArrayList<App>();
		from_accept(list::add);
		return list;
	}

	@SuppressWarnings("null")
	private void from_accept(final Consumer<App> add) {
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
