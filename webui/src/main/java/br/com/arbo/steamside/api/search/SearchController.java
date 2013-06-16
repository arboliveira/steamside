package br.com.arbo.steamside.api.search;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;

@Controller
@RequestMapping("search")
public class SearchController implements ApplicationContextAware {

	@RequestMapping(value = "search.json", params = { "query", "recent=true" })
	@ResponseBody
	public List<AppDTO> recent() {
		return this.continues.continues();
	}

	@SuppressWarnings("static-method")
	@RequestMapping(value = "search.json", params = "query")
	@ResponseBody
	public List<AppDTO> search(
			@RequestParam final String query) {
		if (query == null) throw new NullPointerException();
		return Search.search(query);
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final MutablePicoContainerX container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.continues = new Continues(
				container.getComponent(Continue.class),
				container.getComponent(InMemory_appinfo_vdf.class),
				container.getComponent(CollectionFromVdf.class)
				);
	}

	private Continues continues;

}
