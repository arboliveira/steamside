package br.com.arbo.steamside.webui.wicket.json;

import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.util.time.Duration;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;

public class JsonResource extends AbstractResource {

	private final Factory< ? > factory;

	@Override
	protected ResourceResponse newResourceResponse(final Attributes a) {
		final ResourceResponse r = new ResourceResponse();
		r.setContentType("application/json;charset=UTF-8");
		r.setCacheDuration(Duration.NONE);
		r.setWriteCallback(new WriteCallback() {

			@Override
			public void writeData(final Attributes a) {
				doWriteCallback_writeData(a);
			}
		});
		return r;
	}

	final void doWriteCallback_writeData(final Attributes a) {
		final Object value = factory.produce(a);
		JsonUtils.write(a.getResponse().getOutputStream(), value);
	}

	public JsonResource(final Factory< ? > factory) {
		this.factory = factory;
	}
}
