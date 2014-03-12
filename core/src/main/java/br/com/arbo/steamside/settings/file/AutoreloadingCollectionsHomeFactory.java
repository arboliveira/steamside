package br.com.arbo.steamside.settings.file;

import br.com.arbo.java.util.concurrent.Autoreloading;
import br.com.arbo.steamside.collections.CollectionsHome;
import br.com.arbo.steamside.collections.CollectionsHomeFactory;

public class AutoreloadingCollectionsHomeFactory
		extends Autoreloading<CollectionsHome>
		implements CollectionsHomeFactory {

	public AutoreloadingCollectionsHomeFactory() {
		super(null);
	}
}