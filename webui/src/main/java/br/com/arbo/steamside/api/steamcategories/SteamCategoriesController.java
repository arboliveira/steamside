package br.com.arbo.steamside.api.steamcategories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.apps.Apps.CategoryWithAppsVisitor;
import br.com.arbo.steamside.apps.AppsHome;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.types.Category;
import br.com.arbo.steamside.webui.wicket.SharedConfigConsume;

@Controller
@RequestMapping("steam-categories")
public class SteamCategoriesController implements ApplicationContextAware {

	@RequestMapping("steam-categories.json")
	@ResponseBody
	public List<SteamCategoryDTO> steamCategories() {
		final List<SteamCategoryDTO> dto =
				new ArrayList<SteamCategoryDTO>();
		sharedconfig.data().apps()
				.accept(new CategoryWithAppsVisitor() {

					@Override
					public void visit(final Category each,
							final AppsHome itsApps) {
						dto.add(new SteamCategoryDTO(each));
					}
				});
		return dto;
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final MutablePicoContainerX container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.sharedconfig = container.getComponent(SharedConfigConsume.class);
	}

	private SharedConfigConsume sharedconfig;

}
