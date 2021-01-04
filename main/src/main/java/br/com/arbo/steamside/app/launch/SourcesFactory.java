package br.com.arbo.steamside.app.launch;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.api.app.RunGameCommand;
import br.com.arbo.steamside.api.cloud.CloudController_cloud;
import br.com.arbo.steamside.api.cloud.CloudController_cloud_json;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.api.favorites.FavoritesController_favorites;
import br.com.arbo.steamside.api.favorites.FavoritesController_favorites_json;
import br.com.arbo.steamside.api.kids.KidsController_kids;
import br.com.arbo.steamside.api.kids.KidsController_kids_json;
import br.com.arbo.steamside.api.session.SessionController_session;
import br.com.arbo.steamside.api.session.SessionController_session_json;
import br.com.arbo.steamside.api.steamclient.StatusDTOBuilder;
import br.com.arbo.steamside.api.steamclient.SteamClientController_status;
import br.com.arbo.steamside.bootstrap.Bootstrap;
import br.com.arbo.steamside.bootstrap.BootstrapImpl;
import br.com.arbo.steamside.cloud.CloudSettingsFactory;
import br.com.arbo.steamside.cloud.CloudSettingsFromLocalSettings;
import br.com.arbo.steamside.cloud.CopySteamsideXmlToCloud;
import br.com.arbo.steamside.cloud.CopySteamsideXmlToCloudImpl;
import br.com.arbo.steamside.cloud.Uploader;
import br.com.arbo.steamside.cloud.autoupload.AutoUpload;
import br.com.arbo.steamside.cloud.autoupload.ParallelUpload;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.CollectionsDataSingleton;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.collections.TagsDataSingleton;
import br.com.arbo.steamside.continues.ContinuesFromSteamClientLocalfiles;
import br.com.arbo.steamside.continues.ContinuesRooster;
import br.com.arbo.steamside.data.SteamsideData;
import br.com.arbo.steamside.data.load.FromCloudAndFile;
import br.com.arbo.steamside.data.load.InitialLoad;
import br.com.arbo.steamside.data.singleton.SteamsideDataBootstrap;
import br.com.arbo.steamside.data.singleton.SteamsideDataSingleton;
import br.com.arbo.steamside.favorites.FavoritesOfUser;
import br.com.arbo.steamside.favorites.FromSettings;
import br.com.arbo.steamside.firstrun.FirstRunObserver;
import br.com.arbo.steamside.kids.FromUsername;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.KidsDataSingleton;
import br.com.arbo.steamside.kids.KidsModeDetector;
import br.com.arbo.steamside.settings.Settings;
import br.com.arbo.steamside.settings.SettingsImpl;
import br.com.arbo.steamside.settings.file.File_steamside_xml;
import br.com.arbo.steamside.settings.file.LoadFile;
import br.com.arbo.steamside.settings.file.LoadSteamsideXml;
import br.com.arbo.steamside.settings.file.SaveFile;
import br.com.arbo.steamside.settings.local.File_steamside_local_xml;
import br.com.arbo.steamside.settings.local.File_steamside_local_xml_Supplier;
import br.com.arbo.steamside.settings.local.LocalSettingsFactory;
import br.com.arbo.steamside.settings.local.LocalSettingsLoad;
import br.com.arbo.steamside.settings.local.LocalSettingsPersistence;
import br.com.arbo.steamside.settings.local.LocalSettingsSave;
import br.com.arbo.steamside.steam.client.home.SteamClientHome;
import br.com.arbo.steamside.steam.client.internal.home.SteamClientHomeFromLocalFilesAutoReload;
import br.com.arbo.steamside.steam.client.internal.library.LibraryImpl;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.localfiles.appinfo.File_appinfo_vdf;
import br.com.arbo.steamside.steam.client.localfiles.localconfig.File_localconfig_vdf;
import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.File_sharedconfig_vdf;
import br.com.arbo.steamside.steam.client.protocol.SteamBrowserProtocol;

public class SourcesFactory
{

	private static Sources addComponents(Sources container)
	{
		container
			.sourceImplementor(Bootstrap.class, BootstrapImpl.class)
			.sources(FirstRunObserver.class);

		container
			.sourceImplementor(Settings.class, SettingsImpl.class)
			.sourceImplementor(
				br.com.arbo.steamside.api.app.AppSettings.class,
				br.com.arbo.steamside.api.app.AppSettingsImpl.class);

		container
			.sourceImplementor(Library.class, LibraryImpl.class)
			.sourceImplementor(
				SteamClientHome.class,
				SteamClientHomeFromLocalFilesAutoReload.class);

		container
			.sourceImplementor(
				SteamsideData.class, SteamsideDataSingleton.class)
			.sourceImplementor(
				CollectionsData.class, CollectionsDataSingleton.class)
			.sourceImplementor(
				TagsData.class, TagsDataSingleton.class)
			.sourceImplementor(
				KidsData.class, KidsDataSingleton.class)
			.sources(SteamsideDataBootstrap.class);

		container
			.sourceImplementor(LoadFile.class, LoadSteamsideXml.class)
			.sourceImplementor(InitialLoad.class, FromCloudAndFile.class)
			.sources(File_steamside_xml.class);

		container
			.sourceImplementor(
				LocalSettingsFactory.class,
				LocalSettingsLoad.class)
			.sourceImplementor(
				LocalSettingsPersistence.class,
				LocalSettingsSave.class)
			.sourceImplementor(
				File_steamside_local_xml_Supplier.class,
				File_steamside_local_xml.class)
			.sourceImplementor(
				CloudSettingsFactory.class,
				CloudSettingsFromLocalSettings.class)
			.sourceImplementor(
				CopySteamsideXmlToCloud.class,
				CopySteamsideXmlToCloudImpl.class);

		container
			.sources(
				ParallelUpload.class,
				Uploader.class)
			.sourceImplementor(SaveFile.class, AutoUpload.class);

		container
			.sources(
				File_sharedconfig_vdf.class,
				File_localconfig_vdf.class,
				File_appinfo_vdf.class);

		container
			.sourceImplementor(KidsModeDetector.class, FromUsername.class);

		container
			.sources(
				SteamBrowserProtocol.class,
				RunGameCommand.class);

		container
			.sources(Continues.class)
			.sourceImplementor(
				ContinuesRooster.class,
				ContinuesFromSteamClientLocalfiles.class)
			.sourceImplementor(FavoritesOfUser.class, FromSettings.class);

		container
			.sourceImplementor(
				FavoritesController_favorites.class,
				FavoritesController_favorites_json.class)
			.sourceImplementor(
				SteamClientController_status.class,
				StatusDTOBuilder.class)
			.sourceImplementor(
				SessionController_session.class,
				SessionController_session_json.class)
			.sourceImplementor(
				CloudController_cloud.class,
				CloudController_cloud_json.class)
			.sourceImplementor(
				KidsController_kids.class,
				KidsController_kids_json.class);

		return container;
	}

	public static Sources newInstance()
	{
		return newInstanceInert().sources(
			StartStopBootstrap.class, StartStopSteamClientHomeFromLocalFilesAutoReload.class,
			StartStopSteamsideDataBootstrap.class);
	}

	static Sources newInstanceInert()
	{
		return populate(new Sources());
	}

	public static Sources populate(Sources container)
	{
		return addComponents(container);
	}

}