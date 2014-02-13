package br.com.arbo.steamside.apps;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.Category;

public interface App {

	AppId appid();

	boolean isInCategory(final Category category);

	@NonNull
	String lastPlayedOrCry() throws NeverPlayed;

	@Nullable
	String lastPlayed();

	void accept(final Category.Visitor visitor);
}
