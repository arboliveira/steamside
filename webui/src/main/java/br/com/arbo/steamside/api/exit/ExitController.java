package br.com.arbo.steamside.api.exit;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.exit.Exit;

@RestController
@RequestMapping("exit")
public class ExitController {

	private static void sleepBriefly()
	{
		try
		{
			Thread.sleep(5000);
		}
		catch (final InterruptedException e)
		{
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET)
	public String exit()
	{
		new Thread(
			this::giveTheUserAChanceToSeeTheResultBeforeWeWreakHavocOnJetty,
			"Exit SteamSide").start();
		return "SteamSide, your companion on Steam";
	}

	void giveTheUserAChanceToSeeTheResultBeforeWeWreakHavocOnJetty()
	{
		sleepBriefly();
		exitThenAfterTheSleep();
	}

	private void exitThenAfterTheSleep()
	{
		exit.exit();
	}

	@Inject
	private Exit exit;

}
