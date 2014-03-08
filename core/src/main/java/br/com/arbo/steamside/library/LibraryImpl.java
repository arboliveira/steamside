package br.com.arbo.steamside.library;

import java.util.function.Consumer;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeFactory;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.types.AppId;

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
	public App app(AppId appid) throws NotFound {
		return apps().app(appid);
	}

	@Override
	public void accept(@NonNull Filter filter, @NonNull Consumer<App> visitor) {
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
