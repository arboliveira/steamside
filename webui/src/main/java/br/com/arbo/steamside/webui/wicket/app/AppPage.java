package br.com.arbo.steamside.webui.wicket.app;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.jdt.annotation.NonNull;

import br.com.arbo.org.apache.wicket.markup.html.pages.EmptyPage;
import br.com.arbo.steamside.rungame.RunGame;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.webui.wicket.WicketApplication;

public class AppPage extends EmptyPage {

	public static final String PARAM_command = "command";
	public static final String PARAM_appid = "appid";

	public AppPage(final PageParameters p) {
		super(p);
		setStatelessHint(true);
		final AppId appid = new AppId(getParamStr_appid(p));
		final String command = p.get(PARAM_command).toString();
		if (equalsCommand("run", command)) c_run(appid);
	}

	@NonNull
	private static String getParamStr_appid(final PageParameters p) {
		final org.apache.wicket.util.string.StringValue appidParam =
				p.get(PARAM_appid);
		if (appidParam == null) throw new NullPointerException();

		@NonNull
		@SuppressWarnings("null")
		final String appidParamStr = appidParam.toString();
		return appidParamStr;
	}

	private static void c_run(final AppId appid) {
		final RunGame rungame = WicketApplication.getContainer()
				.getComponent(RunGame.class);
		final boolean up = rungame
				.askSteamToRunGameAndWaitUntilItsUp(appid);
		if (up) letLoadingAnimationRunForJustALittleLonger();
	}

	private static void letLoadingAnimationRunForJustALittleLonger() {
		try {
			Thread.sleep(4000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private static boolean equalsCommand(final String string,
			final String command) {
		return string.equals(command);
	}
}
