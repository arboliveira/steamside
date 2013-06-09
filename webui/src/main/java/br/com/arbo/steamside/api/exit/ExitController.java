package br.com.arbo.steamside.api.exit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.org.picocontainer.MutablePicoContainerX;
import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;

@Controller
@RequestMapping("exit")
public class ExitController implements ApplicationContextAware {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String exit() {
		xtExit();
		return "SteamSide, your companion on Steam";
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		final MutablePicoContainerX container =
				((SteamsideApplicationContext) applicationContext)
						.getContainer();
		this.exit = container.getComponent(Exit.class);
	}

	private void xtExit() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				giveTheUserAChanceToSeeTheResultBeforeWeWreakHavocOnJetty();
			}

		}, "Exit SteamSide").start();

	}

	void giveTheUserAChanceToSeeTheResultBeforeWeWreakHavocOnJetty() {
		sleepBriefly();
		exitThenAfterTheSleep();
	}

	private static void sleepBriefly() {
		try {
			Thread.sleep(5000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void exitThenAfterTheSleep() {
		exit.exit();
	}

	private Exit exit;

}
