package br.com.arbo.steamside.webui.wicket.collection;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.Response;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.apps.Filter;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.collection.ToDTO;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;

public class RenderJson {

	private final Category name;
	private final CollectionFromVdf collectionFromVdf;
	private final InMemory_appinfo_vdf appinfo;

	public RenderJson(
			final Category name,
			final InMemory_appinfo_vdf appinfo,
			final CollectionFromVdf collectionFromVdf) {
		this.name = name;
		this.collectionFromVdf = collectionFromVdf;
		this.appinfo = appinfo;
	}

	public void render(final Response response) {
		final HttpServletResponse httpServletResponse =
				(javax.servlet.http.HttpServletResponse)
				response.getContainerResponse();
		final ServletOutputStream outputStream = getOutputStream(httpServletResponse);
		final CollectionFromVdf r = this.collectionFromVdf;
		final List<App> list = r.query(new FilterCategory(this.name));
		final AppCollectionDTO query = new ToDTO(this.appinfo).convert(list);
		final List<AppDTO> apps = query.apps;
		JsonUtils.write(outputStream, apps);
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