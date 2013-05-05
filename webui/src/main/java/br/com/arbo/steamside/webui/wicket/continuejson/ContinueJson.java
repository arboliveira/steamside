package br.com.arbo.steamside.webui.wicket.continuejson;

import java.util.List;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.IResource.Attributes;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.json.Factory;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class ContinueJson extends ResourceReference {

	private final Factory<List<AppDTO>> factory;

	public ContinueJson(final Continue continues,
			final CollectionFromVdf fetch) {
		super(ContinueJson.class, "continue-json");

		class ContinueJsonResource implements Factory<List<AppDTO>> {

			@Override
			public List<AppDTO> produce(final Attributes a) {
				return fetch.fetch(continues).apps;
			}
		}

		this.factory = new ContinueJsonResource();
	}

	@Override
	public IResource getResource() {
		return new JsonResource(factory);
	}
}