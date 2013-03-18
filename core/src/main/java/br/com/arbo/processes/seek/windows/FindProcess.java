package br.com.arbo.processes.seek.windows;

import org.jvnet.winp.WinProcess;

import br.com.arbo.processes.seek.NotFound;

public class FindProcess {

	public static WinProcess seek(final String exe,
			final String myusername) throws NotFound {
		try {
			return new FindWith_winp(exe, myusername).find();
		} catch (final NotFound e) {
			return new WinProcess(new FindWith_tasklist(exe, myusername).find());
		}
	}

}
