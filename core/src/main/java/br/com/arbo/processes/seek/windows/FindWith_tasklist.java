package br.com.arbo.processes.seek.windows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.arbo.processes.ProcessUtils;
import br.com.arbo.processes.seek.Criteria;
import br.com.arbo.processes.seek.NotFound;

class FindWith_tasklist {

	private final Criteria criteria;

	public FindWith_tasklist(final Criteria criteria) {
		this.criteria = criteria;
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
		final int exeb = tasklist.indexOf(criteria.executable);
		if (exeb == -1) throw new NotFound();
		final int exee = exeb + criteria.executable.length() + 1;
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
		final List<String> command =
				new ArrayList<String>(Arrays.asList(
						"tasklist.exe",
						"/FI",
						"IMAGENAME eq " + criteria.executable));

		if (criteria.usernameNot != null)
			command.addAll(Arrays.asList(
					"/FI",
					"USERNAME ne " + criteria.usernameNot.username
					));

		return ProcessUtils.processout(new ProcessBuilder(command));
	}

}
