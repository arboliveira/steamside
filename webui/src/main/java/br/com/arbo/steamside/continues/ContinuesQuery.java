package br.com.arbo.steamside.continues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.apps.LastPlayedDescending;
import br.com.arbo.steamside.collection.CollectionFromVdf;

public class ContinuesQuery {

	private final CollectionFromVdf from;
	@NonNull
	private final FilterContinues continues;

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
		from.accept(continues, add);
	}

	private static void sort(final List<App> list) {
		Collections.sort(list, new LastPlayedDescending());
		// TODO Prioritize games launched by current user
	}

	public ContinuesQuery(final CollectionFromVdf from,
			final @NonNull FilterContinues continues) {
		this.from = from;
		this.continues = continues;
	}

}
