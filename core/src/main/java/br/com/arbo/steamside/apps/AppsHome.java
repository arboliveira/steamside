package br.com.arbo.steamside.apps;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

public interface AppsHome extends AppsCollection {

	void accept(
			@NonNull final Predicate<App> filter,
			@NonNull final Consumer<App> visitor);

	Stream<Category> allSteamCategories();

	int count();

	App find(AppId appid) throws NotFound;

	Stream<App> findIn(Category category);

}
