package br.com.arbo.steamside.webui.wicket.collection;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.Response;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.collection.CollectionFromVdf;

public class RenderJson {

	private final String name;

	public RenderJson(final String name) {
		this.name = name;
	}

	public void render(final Response response) {
		final HttpServletResponse httpServletResponse =
				(javax.servlet.http.HttpServletResponse)
				response.getContainerResponse();
		final ServletOutputStream outputStream = getOutputStream(httpServletResponse);
		JsonUtils.write(outputStream, CollectionFromVdf.fetch(this.name).apps);
	}

	private static ServletOutputStream getOutputStream(
			final HttpServletResponse httpServletResponse) {
		try {
			return httpServletResponse.getOutputStream();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}