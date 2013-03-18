package br.com.arbo.processes.seek.linux;

import java.io.IOException;

import br.com.arbo.processes.ProcessUtils;
import br.com.arbo.processes.seek.Criteria;
import br.com.arbo.processes.seek.ProcessSeeker;

public class ProcessSeekerLinux implements ProcessSeeker {

	@Override
	public boolean seek(final Criteria criteria) {
		final String exe = criteria.executable;
		final String ignore_grep_itself =
				"'[" + exe.charAt(0) + "]" + exe.substring(1) + "'";
		final String psout = psgrep(ignore_grep_itself);
		return !psout.isEmpty();
	}

	private static String psgrep(final String grepregex) {
		try {
			return ProcessUtils.processout(
					"/bin/sh",
					"-c",
					"ps -o args --no-headers -e | grep " + grepregex
					);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

}
