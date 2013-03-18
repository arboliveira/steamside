package br.com.arbo.processes.seek.windows;

import java.io.IOException;

import br.com.arbo.processes.ProcessUtils;
import br.com.arbo.processes.seek.NotFound;

class FindWith_tasklist {

	private final String myusername;
	private final String executable;

	public FindWith_tasklist(final String executable, final String myusername) {
		this.executable = executable;
		this.myusername = myusername;
	}

	int find() throws NotFound {
		try {
			return findOrCry();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private int findOrCry()
			throws IOException, NotFound {
		final String tasklist = run_tasklist();
		final int pid = extract_pid(tasklist);
		return pid;
	}

	private int extract_pid(final String tasklist) throws NotFound {
		final int exeb = tasklist.indexOf(executable);
		if (exeb == -1) throw new NotFound();
		final int exee = exeb + executable.length() + 1;
		int i = exee;
		while (tasklist.charAt(i) == ' ')
			i++;
		final int pidb = i;
		while (tasklist.charAt(i) != ' ')
			i++;
		final int pide = i;
		final int pid = Integer.valueOf(tasklist.substring(pidb, pide))
				.intValue();
		return pid;
	}

	private String run_tasklist() throws IOException {
		return ProcessUtils.processout(
				"tasklist.exe",
				"/FI",
				"USERNAME ne " + myusername,
				"/FI",
				"IMAGENAME eq " +
						executable
				);
	}

}
