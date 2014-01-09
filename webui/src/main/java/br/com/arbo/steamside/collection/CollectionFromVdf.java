package br.com.arbo.steamside.collection;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.Apps;
import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DataFactory_sharedconfig_vdf;

public class CollectionFromVdf {

	public void accept(@NonNull final Filter filter,
			@NonNull final AppVisitor what) {
		final Apps apps = sharedconfig.data().apps();
		apps.accept(filter, what);
	}

	private final DataFactory_sharedconfig_vdf sharedconfig;

	@Inject
	public CollectionFromVdf(
			final DataFactory_sharedconfig_vdf sharedconfig) {
		this.sharedconfig = sharedconfig;
	}

}