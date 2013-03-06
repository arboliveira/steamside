package br.com.arbo.steamside.steam.client.executable;

import org.jvnet.winp.WinProcess;

class FindSteam {

	static WinProcess find_steam_exe(final String myusername) {
		try {
			return new FindWith_winp(myusername).find();
		} catch (final NotFound e) {
			return new FindWith_tasklist(myusername).find();
		}
	}

}
