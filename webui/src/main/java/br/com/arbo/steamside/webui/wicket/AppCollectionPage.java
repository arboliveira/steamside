package br.com.arbo.steamside.webui.wicket;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.webui.wicket.AppCollectionPage.State.NotSuper;
import br.com.arbo.steamside.webui.wicket.collection.Command;
import br.com.arbo.steamside.webui.wicket.collection.Params;
import br.com.arbo.steamside.webui.wicket.collection.RenderJson;

public class AppCollectionPage extends WebPage {

	interface State {

		void configureResponse(WebResponse r);

		void onRender();

		static class NotSuper extends RuntimeException {
			//
		}
	}

	private final State state;

	public AppCollectionPage(final PageParameters p) {
		super(p);
		setStatelessHint(true);
		final String command = p.get(Params.PARAM_command).toString();
		final Command c = Command.of(command);
		this.state = state(c, p);
	}

	@Override
	protected void configureResponse(final WebResponse r) {
		super.configureResponse(r);
		state.configureResponse(r);
	}

	@Override
	protected void onRender() {
		try {
			state.onRender();
		} catch (final NotSuper e) {
			return;
		}
		super.onRender();
	}

	private State state(final Command c, final PageParameters p) {
		if (c == Command.JSON) return new JsonState(p, this);
		return new DefaultState();
	}

	static class DefaultState implements State {

		@Override
		public void configureResponse(final WebResponse r) {
			// do nothing
		}

		@Override
		public void onRender() {
			// do nothing
		}

	}

	static class JsonState implements State {

		private final Category name;
		private final WebPage page;
		private final CollectionFromVdf collectionFromVdf;

		public JsonState(final PageParameters p,
				final WebPage page) {
			this.page = page;
			this.name =
					new Category(
							p.get(Params.PARAM_collectionname).toString()
					);
			final MutablePicoContainerX container =
					WicketApplication.getContainer();
			this.collectionFromVdf = new CollectionFromVdf(
					container.getComponent(InMemory_appinfo_vdf.class),
					container.getComponent(SharedConfigConsume.class));
		}

		@Override
		public void configureResponse(final WebResponse r) {
			r.setContentType("application/json;charset=UTF-8");
		}

		@Override
		public void onRender() {
			new RenderJson(this.name, collectionFromVdf)
					.render(page.getResponse());
			throw new NotSuper();
		}

	}
}