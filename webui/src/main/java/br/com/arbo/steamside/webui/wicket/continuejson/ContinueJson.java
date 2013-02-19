package br.com.arbo.steamside.webui.wicket.continuejson;

import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.continues.Continue.Needs;
import br.com.arbo.steamside.webui.wicket.json.JsonResource;

public class ContinueJson extends ResourceReference {

	private final Needs needsContinue;

	public ContinueJson(final Continue.Needs needsContinue) {
		super(ContinueJson.class, "continue-json");
		this.needsContinue = needsContinue;
	}

	@Override
	public IResource getResource() {
		return new JsonResource(new ContinueJsonResource(needsContinue));
	}
}