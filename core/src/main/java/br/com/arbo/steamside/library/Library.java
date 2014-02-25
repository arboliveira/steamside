package br.com.arbo.steamside.library;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppsHome.CategoryWithAppsVisitor;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.types.AppId;

public interface Library {

	int count();

	App app(AppId appid) throws NotFound;

	void accept(@NonNull Filter filter, @NonNull App.Visitor visitor);

	void accept(final CategoryWithAppsVisitor visitor);

}
