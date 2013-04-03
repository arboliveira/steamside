package br.com.arbo.steamside.webui.wicket.favorites.json;

import java.util.List;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.json.Factory;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class FavoritesJson extends ResourceReference {

	private final Factory<List<AppDTO>> fetch;

	public FavoritesJson(final Favorites favorites,
			final CollectionFromVdf fetch) {
		super(FavoritesJson.class, "favorites-json");

		class FavoritesJsonResource implements Factory<List<AppDTO>> {

			@Override
			public List<AppDTO> produce(final Attributes a) {
				return fetch.fetch(favorites).apps;
			}
		}

		this.fetch = new FavoritesJsonResource();
	}

	@Override
	public IResource getResource() {
		return new JsonResource(fetch);
	}
}