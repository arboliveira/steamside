package br.com.arbo.steamside.library;

import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.apps.Apps.CategoryWithAppsVisitor;
import br.com.arbo.steamside.apps.Filter;

public interface Library {

	int count();

	void accept(@NonNull Filter filter, @NonNull AppVisitor visitor);

	void accept(final CategoryWithAppsVisitor visitor);
}
