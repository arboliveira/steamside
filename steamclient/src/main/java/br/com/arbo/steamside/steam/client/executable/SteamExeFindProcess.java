package br.com.arbo.steamside.steam.client.executable;

import java.util.function.Consumer;

import org.apache.commons.lang3.SystemUtils;
import org.jvnet.winp.WinProcess;

import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.NotFound;
import br.com.arbo.opersys.processes.seek.windows.FindProcess;
import br.com.arbo.opersys.processes.seek.windows.WinProcessX;

public class SteamExeFindProcess {

	public static void seek(Criteria criteria, Consumer<WinProcessX> what)
	{
		if (!SystemUtils.IS_OS_WINDOWS) return;

		criteria.executable = "steam.exe";

		WinProcess.enableDebugPrivilege();
		final WinProcessX steam_exe;
		try
		{
			steam_exe = FindProcess.seek(criteria);
		}
		catch (final NotFound e)
		{
			return;
		}

		what.accept(steam_exe);
	}

}
