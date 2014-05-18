package br.com.arbo.processes.seek.windows;

import br.com.arbo.processes.seek.Criteria;
import br.com.arbo.processes.seek.NotFound;
import br.com.arbo.processes.seek.ProcessSeeker;

public class ProcessSeekerWindows implements ProcessSeeker {

	@Override
	public boolean seek(final Criteria criteria) {
		try {
			FindProcess.seek(criteria);
			return true;
		} catch (final NotFound e) {
			return false;
		}
	}
}
