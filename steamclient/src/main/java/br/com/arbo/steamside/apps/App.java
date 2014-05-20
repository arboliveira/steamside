package br.com.arbo.steamside.apps;

import java.util.function.Consumer;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.AppName;
import br.com.arbo.steamside.types.AppType;
import br.com.arbo.steamside.types.SteamCategory;

public interface App {

	@NonNull
	AppId appid();

	@NonNull
	String executable() throws NotAvailableOnThisPlatform;

	void forEachCategory(final Consumer<SteamCategory> visitor);

	default boolean isGame()
	{
		try {
			return type().isGame();
		}
		catch (MissingFrom_appinfo_vdf e) {
			return false;
		}
	}

	boolean isInCategory(final SteamCategory category);

	@Nullable
	String lastPlayed();

	@NonNull
	String lastPlayedOrCry() throws NeverPlayed;

	@NonNull
	AppName name() throws MissingFrom_appinfo_vdf;

	@NonNull
	AppType type() throws MissingFrom_appinfo_vdf;

}
