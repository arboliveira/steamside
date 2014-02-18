package br.com.arbo.steamside.library;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeFactory;
import br.com.arbo.steamside.apps.Filter;

public class LibraryImpl implements Library {

	private final AppsHomeFactory appsHomeFactory;

	@Inject
	public LibraryImpl(AppsHomeFactory appsHomeFactory) {
		this.appsHomeFactory = appsHomeFactory;
	}

	@Override
	public int count() {
		return apps().count();
	}

	@Override
	public void accept(@NonNull Filter filter, @NonNull App.Visitor visitor) {
		apps().accept(filter, visitor);
	}

	@Override
	public void accept(AppsHome.CategoryWithAppsVisitor visitor) {
		apps().accept(visitor);
	}

	private final AppsHome apps() {
		return appsHomeFactory.get();
	}

}
