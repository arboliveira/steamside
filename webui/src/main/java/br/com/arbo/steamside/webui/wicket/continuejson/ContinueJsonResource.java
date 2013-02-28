package br.com.arbo.steamside.webui.wicket.continuejson;

import java.util.List;

import org.apache.wicket.request.resource.IResource.Attributes;

import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.webui.appdto.AppDTO;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

class ContinueJsonResource implements JsonResource.Needs<List<AppDTO>> {

	private final Continue.Needs needsContinue;

	ContinueJsonResource(final Continue.Needs needsContinue) {
		this.needsContinue = needsContinue;
	}

	@Override
	public List<AppDTO> fetchValue(final Attributes a) {
		return new Continue(needsContinue).fetch();
	}
}