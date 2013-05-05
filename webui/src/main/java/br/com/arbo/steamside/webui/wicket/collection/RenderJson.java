package br.com.arbo.steamside.webui.wicket.collection;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.Response;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.collection.Filter;
import br.com.arbo.steamside.types.Category;

public class RenderJson {

	private final Category name;
	private final CollectionFromVdf collectionFromVdf;

	public RenderJson(final Category name,
			final CollectionFromVdf collectionFromVdf) {
		this.name = name;
		this.collectionFromVdf = collectionFromVdf;
	}

	public void render(final Response response) {
		final HttpServletResponse httpServletResponse =
				(javax.servlet.http.HttpServletResponse)
				response.getContainerResponse();
		final ServletOutputStream outputStream = getOutputStream(httpServletResponse);
		JsonUtils.write(outputStream,
				this.collectionFromVdf.fetch(
						new FilterCategory(this.name)).apps);
	}

	private static ServletOutputStream getOutputStream(
			final HttpServletResponse httpServletResponse) {
		try {
			return httpServletResponse.getOutputStream();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	static class FilterCategory implements Filter {

		private final Category category;

		FilterCategory(final Category category) {
			this.category = category;
		}

		@Override
		public void consider(final App app) throws Reject {
			if (!app.isInCategory(category)) throw new Reject();
		}

	}

}