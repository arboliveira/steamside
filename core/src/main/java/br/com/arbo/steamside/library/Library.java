package br.com.arbo.steamside.library;

import java.util.function.Consumer;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppsHome.CategoryWithAppsVisitor;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.types.AppId;

public interface Library {

	int count();

	App find(AppId appid) throws NotFound;

	void accept(
			@NonNull Predicate<App> filter,
			@NonNull Consumer<App> visitor
			);

	void accept(final CategoryWithAppsVisitor visitor);

}
