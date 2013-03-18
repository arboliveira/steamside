package br.com.arbo.processes.seek.windows;

import br.com.arbo.processes.seek.NotFound;
import br.com.arbo.processes.seek.ProcessSeeker;

public class ProcessSeekerWindows implements ProcessSeeker {

	@Override
	public boolean seek(final String executable, final String username) {
		try {
			FindProcess.seek(executable, username);
			return true;
		} catch (final NotFound e) {
			return false;
		}
	}

}
