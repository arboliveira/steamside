package br.com.arbo.steamside.apps;

import java.util.Comparator;

import org.eclipse.jdt.annotation.NonNull;

public final class LastPlayedDescending implements
		Comparator<App> {

	@Override
	public int compare(final App a1, final App a2) {
		final @NonNull
		String l1 = a1.lastPlayedOrCry();
		final @NonNull
		String l2 = a2.lastPlayedOrCry();
		final Long long2 = Long.valueOf(l2);
		final Long long1 = Long.valueOf(l1);
		return long2.compareTo(long1);
	}
}