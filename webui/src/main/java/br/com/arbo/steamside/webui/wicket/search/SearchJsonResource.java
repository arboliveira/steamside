package br.com.arbo.steamside.webui.wicket.search;

import org.apache.wicket.request.Response;
import org.apache.wicket.request.resource.AbstractResource;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.search.ResultsDTO;
import br.com.arbo.steamside.search.Search;

class SearchJsonResource extends AbstractResource {

	@Override
	protected ResourceResponse newResourceResponse(final Attributes a) {
		final ResourceResponse r = new ResourceResponse();
		r.setContentType("application/json;charset=UTF-8");
		r.setWriteCallback(new WriteCallback() {

			@Override
			public void writeData(final Attributes a) {
				SearchJsonResource.writeData(a);
			}
		});
		return r;
	}

	static void writeData(final Attributes a) {
		final String query = a.getParameters().get("query").toString();
		final ResultsDTO results = Search.search(query);
		final Response response = a.getResponse();
		JsonUtils.write(response.getOutputStream(), results.apps);
	}

	public static void main(final String[] args) {
		System.out.println(JsonUtils.asString(Search.search("mishap").apps));
	}
}