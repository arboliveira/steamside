package br.com.arbo.steamside.api.continues;

import java.util.Collections;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.collection.ToDTO;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;
import br.com.arbo.steamside.webui.appdto.AppCollectionDTO;
import br.com.arbo.steamside.webui.appdto.AppDTO;

@Controller
@RequestMapping("continues")
public class ContinuesController implements ApplicationContextAware {

	@RequestMapping("continues.json")
	@ResponseBody
	public List<AppDTO> continues() {
		@SuppressWarnings("null")
		final List<App> list = from.query(continues);
		sort(list);
		final AppCollectionDTO dto = new ToDTO(appinfo).convert(list);
		return dto.apps;
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final MutablePicoContainerX container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.continues = container.getComponent(Continue.class);
		this.appinfo = container.getComponent(InMemory_appinfo_vdf.class);
		this.from = container.getComponent(CollectionFromVdf.class);
	}

	private static void sort(final List<App> list) {
		Collections.sort(list, new App.LastPlayedDescending());
		// TODO Prioritize games launched by current user
	}

	public ContinuesController() {
		// for Spring
	}

	public ContinuesController(
			@NonNull final Continue continues,
			final InMemory_appinfo_vdf appinfo,
			final CollectionFromVdf from) {
		super();
		this.continues = continues;
		this.appinfo = appinfo;
		this.from = from;
	}

	private Continue continues;
	private InMemory_appinfo_vdf appinfo;
	private CollectionFromVdf from;

}
