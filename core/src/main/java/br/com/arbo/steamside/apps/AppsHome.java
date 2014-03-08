package br.com.arbo.steamside.apps;

import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

public interface AppsHome extends AppsCollection {

	App app(AppId appid) throws NotFound;

	void accept(
			@NonNull final Filter filter, @NonNull final Consumer<App> visitor);

	void accept(CategoryWithAppsVisitor visitor);

	public interface CategoryWithAppsVisitor {

		void visit(Category each, AppsCollection itsApps);
	}

}
