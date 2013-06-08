package br.com.arbo.steamside.api.exit;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.exit.Exit;
import br.com.arbo.steamside.spring.SteamsideApplicationContext;

@Controller
@RequestMapping("exit")
public class ExitController implements ApplicationContextAware {

	private Exit exit;

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String exit() {
		giveTheUserAChanceToSeeTheResultBeforeWeWreakHavocOnJetty();
		return "SteamSide, your companion on Steam";
	}

	private void giveTheUserAChanceToSeeTheResultBeforeWeWreakHavocOnJetty() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				sleepBriefly();
				exitInvokedFromAnotherThread();
			}

			private void sleepBriefly() {
				try {
					Thread.sleep(2000);
				} catch (final InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

		}, "Exit SteamSide").start();

	}

	void exitInvokedFromAnotherThread() {
		exit.exit();
	}

	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext)
			throws BeansException {
		this.exit =
				((SteamsideApplicationContext) applicationContext)
						.getExit();
	}

}
