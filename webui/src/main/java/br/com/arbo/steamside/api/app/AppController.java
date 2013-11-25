package br.com.arbo.steamside.api.app;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.types.AppId;

@Controller
@RequestMapping(br.com.arbo.steamside.mapping.App.app)
public class AppController {

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

	private static void letLoadingAnimationRunForJustALittleLonger() {
		try {
			Thread.sleep(4000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Inject
	private RunGame rungame;
}
