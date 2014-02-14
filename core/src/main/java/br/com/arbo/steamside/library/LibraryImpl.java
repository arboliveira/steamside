package br.com.arbo.steamside.library;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.apps.Apps.CategoryWithAppsVisitor;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.DataFactory_sharedconfig_vdf;

public class LibraryImpl implements Library {

	@Inject
	public LibraryImpl(DataFactory_sharedconfig_vdf config) {
		this.config = config;
	}

	private final DataFactory_sharedconfig_vdf config;

	@Override
	public int count() {
		return config.data().apps().count();
	}

	@Override
	public void accept(@NonNull Filter filter, @NonNull AppVisitor visitor) {
		config.data().apps().accept(filter, visitor);
	}

	@Override
	public void accept(CategoryWithAppsVisitor visitor) {
		config.data().apps().accept(visitor);
	}

}
