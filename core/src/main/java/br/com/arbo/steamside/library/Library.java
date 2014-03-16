package br.com.arbo.steamside.library;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

public interface Library {

	void accept(
			@NonNull Predicate<App> filter,
			@NonNull Consumer<App> visitor
			);

	Stream<Category> allSteamCategories();

	int count();

	App find(AppId appid) throws NotFound;

	Stream<App> findIn(Category category);

}
