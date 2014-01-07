package br.com.arbo.steamside.apps;

public class FilterNeverPlayed implements Filter {

	@Override
	public void consider(final App app) throws Reject {
		try {
			app.lastPlayedOrCry();
		} catch (final NeverPlayed ex) {
			throw new Reject();
		}
	}

}
