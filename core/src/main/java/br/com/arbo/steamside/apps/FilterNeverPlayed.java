package br.com.arbo.steamside.apps;

import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.NeverPlayed;

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
