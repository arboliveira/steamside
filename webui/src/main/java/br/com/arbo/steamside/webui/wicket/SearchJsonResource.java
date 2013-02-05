package br.com.arbo.steamside.webui.wicket;

import java.io.IOException;

import org.apache.wicket.request.resource.AbstractResource;
import org.codehaus.jackson.map.ObjectMapper;

import br.com.arbo.steamside.search.ResultsDTO;
import br.com.arbo.steamside.search.Search;

public class SearchJsonResource extends AbstractResource {

	@Override
	protected ResourceResponse newResourceResponse(final Attributes a) {
		final ResourceResponse r = new ResourceResponse();
		r.setContentType("application/json;charset=UTF-8");
		r.setWriteCallback(new WriteCallback() {

			@Override
			public void writeData(final Attributes a) {
				final String query = a.getParameters().get("query").toString();
				final ResultsDTO results = Search.search(query);
				final String json = toJson(results);
				a.getResponse().write(json);
			}
		});
		return r;
	}

	static String toJson(final ResultsDTO results) {
		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(
					results.apps);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(final String[] args) {
		System.out.println(toJson(Search.search("mishap")));
	}
}