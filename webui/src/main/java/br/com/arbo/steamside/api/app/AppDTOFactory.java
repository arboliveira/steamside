package br.com.arbo.steamside.api.app;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.steam.client.apps.App;
import br.com.arbo.steamside.steam.client.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.steam.client.apps.NotFound;
import br.com.arbo.steamside.steam.client.library.Library;
import br.com.arbo.steamside.steam.client.localfiles.appcache.entry.NotAvailableOnThisPlatform;
import br.com.arbo.steamside.steam.client.types.AppId;

public class AppDTOFactory {

	public static AppDTO valueOf(
			final AppId appid, final Library library,
			TagsQueries queries)
			throws MissingFrom_appinfo_vdf, NotFound
	{
		final App app = library.find(appid);

		final Stream<AppTagDTO> dtos = queries.tags(appid)
				.map(CollectionI::name).map(AppTagDTO::new);

		List<AppTagDTO> list = dtos.collect(
				LinkedList::new, LinkedList::add,
				LinkedList::addAll);

		boolean unavailable = isUnavailable(app);

		return new AppDTO(appid, app.name(), list, unavailable);
	}

	private static boolean isUnavailable(final App app)
	{
		try
		{
			app.executable();
			return false;
		}
		catch (NotAvailableOnThisPlatform not)
		{
			return true;
		}
	}

}
