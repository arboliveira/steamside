package br.com.arbo.steamside.library;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.apps.AppsHomeFactory;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

public class LibraryImpl implements Library {

	private final AppsHomeFactory appsHomeFactory;

	@Inject
	public LibraryImpl(AppsHomeFactory appsHomeFactory) {
		this.appsHomeFactory = appsHomeFactory;
	}

	@Override
	public void accept(
			@NonNull Predicate<App> filter,
			@NonNull Consumer<App> visitor) {
		apps().accept(filter, visitor);
	}

	@Override
	public Stream<Category> allSteamCategories() {
		return apps().allSteamCategories();
	}

	@Override
	public int count() {
		return apps().count();
	}

	@Override
	public App find(AppId appid) throws NotFound {
		return apps().find(appid);
	}

	@Override
	public Stream<App> findIn(Category category) {
		return apps().findIn(category);
	}

	private final AppsHome apps() {
		return appsHomeFactory.get();
	}

}
