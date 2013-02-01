package br.com.arbo.steamside.webui.wicket;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.request.resource.AbstractResource;

public class SearchJsonResource extends AbstractResource {

	@Override
	protected ResourceResponse newResourceResponse(final Attributes a) {
		final ResourceResponse r = new ResourceResponse();
		r.setContentType("application/json");
		r.setWriteCallback(new WriteCallback() {

			@Override
			public void writeData(final Attributes a) {
				final String query = a.getParameters().get("query").toString();
				a.getResponse()
						.write(
								"[ " +
										"{ \"name\": \"" + query + "\" }" +
										", " +
										"{ \"name\": \""
										+ StringUtils.reverse(query) + "\" }" +
										" ]"
						);
			}
		});
		return r;
	}

}