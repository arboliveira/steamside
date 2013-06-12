package br.com.arbo.steamside.api.continues;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.collection.CollectionFromVdf;
import br.com.arbo.steamside.continues.Continue;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.steam.client.localfiles.appcache.InMemory_appinfo_vdf;

@Controller
@RequestMapping("continues")
public class ContinuesController implements ApplicationContextAware {

	@RequestMapping("continues.json")
	@ResponseBody
	public List<AppDTO> continues() {
		return this.continues.continues();
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
