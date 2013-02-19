package br.com.arbo.steamside.webui.wicket.continuejson;

import org.apache.wicket.request.resource.IResource.Attributes;

import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

class ContinueJsonResource implements JsonResource.Needs {

	private final Continue.Needs needsContinue;

	ContinueJsonResource(final Continue.Needs needsContinue) {
		this.needsContinue = needsContinue;
	}

	@Override
	public Object fetchValue(final Attributes a) {
		return new Continue(needsContinue).fetch();
	}
}