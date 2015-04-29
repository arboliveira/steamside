package br.com.arbo.opersys.processes.seek.windows;

import java.util.Optional;

import org.jvnet.winp.WinProcess;
import org.jvnet.winp.WinpException;

import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.NotFound;

class FindWith_winp {

	FindWith_winp(final Criteria criteria)
	{
		this.criteria = criteria;
	}

	WinProcessX find() throws NotFound
	{
		final WinProcessX exe = find_exe();
		filterUsernameNot(exe);
		return exe;
	}

	private void filterUsernameNot(final WinProcessX exe) throws NotFound
	{
		if (criteria.usernameNot.equals(Optional.of(exe.username())))
			throw new NotFound();
	}

	private WinProcessX find_exe() throws NotFound
	{
		for (final WinProcess p : WinProcess.all())
			if (isExe(p)) return new WinProcessX(p);
		throw new NotFound();
	}

	private boolean isExe(final WinProcess p)
	{
		final String commandLine;
		try
		{
			commandLine = p.getCommandLine();
		}
		catch (final WinpException e)
		{
			final int code = e.getWin32ErrorCode();
			if (code == 87 || code == 5 || code == 299) return false;
			throw e;
		}
		return commandLine.contains(criteria.executable);
	}

	private final Criteria criteria;

}
