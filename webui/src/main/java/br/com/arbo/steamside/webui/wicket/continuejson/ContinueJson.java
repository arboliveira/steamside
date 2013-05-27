package br.com.arbo.steamside.webui.wicket.continuejson;

import java.util.Collections;
import java.util.List;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceReference;
import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.collection.ToDTO;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.json.Factory;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class ContinueJson extends ResourceReference {

	private final Factory<List<AppDTO>> factory;
	private final InMemory_appinfo_vdf appinfo;

	public ContinueJson(
			@NonNull final Continue continues,
			final InMemory_appinfo_vdf appinfo,
			final CollectionFromVdf from) {
		super(ContinueJson.class, "continue-json");

		this.appinfo = appinfo;
		this.factory = new Factory<List<AppDTO>>() {

			@Override
			public List<AppDTO> produce(final Attributes a) {
				return fetch(continues, from);
			}
		};
	}

	@Override
	public IResource getResource() {
		return new JsonResource(factory);
	}

	List<AppDTO> fetch(@NonNull final Continue continues,
			final CollectionFromVdf from) {
		final List<App> list = from.query(continues);
		sort(list);
		final AppCollectionDTO dto = new ToDTO(this.appinfo).convert(list);
		return dto.apps;
	}

	public static void sort(final List<App> list) {
		Collections.sort(list, new App.LastPlayedDescending());
		// TODO Prioritize games launched by current user
	}
}