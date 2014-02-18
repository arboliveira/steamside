package br.com.arbo.steamside.library;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.AppsHome.CategoryWithAppsVisitor;
import br.com.arbo.steamside.apps.Filter;

public interface Library {

	int count();

	void accept(@NonNull Filter filter, @NonNull App.Visitor visitor);

	void accept(final CategoryWithAppsVisitor visitor);
}
