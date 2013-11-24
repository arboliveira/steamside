package br.com.arbo.steamside.api.app;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;
import br.com.arbo.steamside.types.AppId;

@Controller
@RequestMapping(br.com.arbo.steamside.mapping.App.app)
public class AppController implements ApplicationContextAware {

	@SuppressWarnings("static-method")
	@RequestMapping("{appid}/"
			+ br.com.arbo.steamside.mapping.App.run)
	public void run(
			@NonNull @PathVariable final String appid) {
		final boolean up = rungame
				.askSteamToRunGameAndWaitUntilItsUp(
				new AppId(appid));
		if (up) letLoadingAnimationRunForJustALittleLonger();
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final ContainerWeb container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.rungame = container.getComponent(RunGame.class);
	}

	private static void letLoadingAnimationRunForJustALittleLonger() {
		try {
			Thread.sleep(4000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private RunGame rungame;
}
