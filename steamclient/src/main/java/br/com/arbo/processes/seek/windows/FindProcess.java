package br.com.arbo.processes.seek.windows;

import org.jvnet.winp.WinProcess;

import br.com.arbo.processes.seek.Criteria;
import br.com.arbo.processes.seek.NotFound;

public class FindProcess {

	public static WinProcess seek(
			final Criteria criteria)
			throws NotFound {
		try {
			return new FindWith_winp(criteria).find();
		} catch (final NotFound e) {
			return new WinProcess(
					new FindWith_tasklist(criteria).find());
		}
	}

}
