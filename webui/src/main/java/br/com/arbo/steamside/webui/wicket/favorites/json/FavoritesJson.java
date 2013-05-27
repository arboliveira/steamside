package br.com.arbo.steamside.webui.wicket.favorites.json;

import java.util.List;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceReference;
import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.collection.ToDTO;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.json.Factory;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class FavoritesJson extends ResourceReference {

	private final Factory<List<AppDTO>> factory;

	public FavoritesJson(
			@NonNull final Favorites favorites,
			final InMemory_appinfo_vdf appinfo,
			final CollectionFromVdf from) {
		super(FavoritesJson.class, "favorites-json");

		this.factory = new Factory<List<AppDTO>>() {

			@Override
			public List<AppDTO> produce(final Attributes a) {
				return fetch(favorites, from, appinfo);
			}
		};
	}

	@Override
	public IResource getResource() {
		return new JsonResource(factory);
	}

	static List<AppDTO> fetch(
			@NonNull final Favorites favorites,
			final CollectionFromVdf from, final InMemory_appinfo_vdf appinfo) {
		final List<App> list = from.query(favorites);
		final AppCollectionDTO dto = new ToDTO(appinfo).convert(list);
		return dto.apps;
	}
}