package br.com.arbo.steamside.webui.wicket;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.arbo.org.codehaus.jackson.map.JsonUtils;
import br.com.arbo.steamside.search.ResultDTO;
import br.com.arbo.steamside.search.ResultsDTO;
import br.com.arbo.steamside.vdf.App;
import br.com.arbo.steamside.vdf.Apps;
import br.com.arbo.steamside.vdf.Apps.Visitor;
import br.com.arbo.steamside.vdf.SharedconfigVdfLocation;

public class AppCollectionPage extends WebPage {

	public static final String PARAM_command = "command";
	public static final String PARAM_collectionname = "collectionname";
	private final Command command;
	private final String name;

	public AppCollectionPage(final PageParameters p) {
		super(p);
		final String name = p.get(PARAM_collectionname).toString();
		final String command = p.get(PARAM_command).toString();
		this.name = name;
		this.command = Command.of(command);
	}

	@Override
	protected void configureResponse(final WebResponse r) {
		super.configureResponse(r);
		if (command == Command.JSON)
			r.setContentType("application/json;charset=UTF-8");
	}

	@Override
	protected void onRender() {
		if (command == Command.JSON) {
			renderJson();
			return;
		}
		super.onRender();
	}

	private void renderJson() {
		final HttpServletResponse httpServletResponse =
				(javax.servlet.http.HttpServletResponse)
				getResponse().getContainerResponse();
		final PrintWriter pw = new PrintWriter(
				getOutputStream(httpServletResponse));
		try {
			pw.write(getJson());
		} finally {
			pw.close();
		}
	}

	private static ServletOutputStream getOutputStream(
			final HttpServletResponse httpServletResponse) {
		try {
			return httpServletResponse.getOutputStream();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getJson() {
		// TODO Auto-generated method stub
		return JsonUtils.asString(CollectionFromVdf.fetch(this.name).apps);
	}

	private enum Command {
		JSON;

		static Command of(final String command) {
			if (equalsCommand("apps.json", command))
				return Command.JSON;
			return null;
		}

		private static boolean equalsCommand(final String string,
				final String command) {
			return string.equals(command);
		}
	}

	private static class CollectionFromVdf {

		public static ResultsDTO fetch(final String name) {

			final Apps apps = SharedconfigVdfLocation.make().apps();
			final Filter filter = new Filter(name, apps);
			apps.accept(filter);

			final ResultsDTO results = new ResultsDTO();
			results.apps = filter.list;
			return results;
		}

	}

	static class Filter implements Visitor {

		private final String name;
		private final Apps apps;
		final List<ResultDTO> list = new ArrayList<ResultDTO>(20);

		public Filter(final String name, final Apps apps) {
			this.name = name;
			this.apps = apps;
		}

		@Override
		public void each(final String appid) {
			final App app = apps.app(appid);
			if (app.isFavorite()) {
				final ResultDTO dto =
						new ResultDTO(appid,
								"Oh no! I was parsing the name from the store page!");
				list.add(dto);
			}
		}
	}
}
