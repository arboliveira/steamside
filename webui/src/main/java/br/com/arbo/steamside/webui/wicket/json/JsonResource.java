package br.com.arbo.steamside.webui.wicket.json;

import org.apache.wicket.request.resource.AbstractResource;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;

public class JsonResource extends AbstractResource {

	private final Needs< ? > needs;

	public interface Needs<T> {

		T fetchValue(Attributes a);

	}

	@Override
	protected ResourceResponse newResourceResponse(final Attributes a) {
		final ResourceResponse r = new ResourceResponse();
		r.setContentType("application/json;charset=UTF-8");
		r.setWriteCallback(new WriteCallback() {

			@Override
			public void writeData(final Attributes a) {
				doWriteCallback_writeData(a);
			}
		});
		return r;
	}

	final void doWriteCallback_writeData(final Attributes a) {
		final Object value = needs.fetchValue(a);
		JsonUtils.write(a.getResponse().getOutputStream(), value);
	}

	public JsonResource(final Needs< ? > needs) {
		this.needs = needs;
	}
}
