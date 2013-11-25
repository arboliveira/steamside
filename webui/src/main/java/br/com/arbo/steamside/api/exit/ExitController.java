package br.com.arbo.steamside.api.exit;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.exit.Exit;

@Controller
@RequestMapping("exit")
public class ExitController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String exit() {
		xtExit();
		return "SteamSide, your companion on Steam";
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

	@Inject
	private Exit exit;

}
