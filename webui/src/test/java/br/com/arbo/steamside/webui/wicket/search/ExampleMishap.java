package br.com.arbo.steamside.webui.wicket.search;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.webui.wicket.search.Search;

public class ExampleMishap {

	public static void main(final String[] args) {
		System.out.println(JsonUtils.asString(Search.search("mishap").apps));
	}

}
