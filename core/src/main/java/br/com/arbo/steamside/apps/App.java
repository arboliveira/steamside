package br.com.arbo.steamside.apps;

import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;
import br.com.arbo.steamside.types.Category;

public interface App {

	@NonNull
	AppId appid();

	@NonNull
	AppName name() throws MissingFrom_appinfo_vdf;

	@NonNull
	String executable() throws NotAvailableOnThisPlatform;

	@NonNull
	String lastPlayedOrCry() throws NeverPlayed;

	@Nullable
	String lastPlayed();

	boolean isInCategory(final Category category);

	void forEachCategory(final Consumer<Category> visitor);

}
