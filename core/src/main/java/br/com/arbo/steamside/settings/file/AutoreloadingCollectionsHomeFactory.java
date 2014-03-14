package br.com.arbo.steamside.settings.file;

import br.com.arbo.java.util.concurrent.Parallel;
import br.com.arbo.steamside.collections.CollectionsHome;
import br.com.arbo.steamside.collections.CollectionsHomeFactory;

public class AutoreloadingCollectionsHomeFactory
		extends Parallel<CollectionsHome>
		implements CollectionsHomeFactory {

	public AutoreloadingCollectionsHomeFactory() {
		super(null);
	}
}