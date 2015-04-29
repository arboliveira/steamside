package br.com.arbo.opersys.processes.seek.windows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.arbo.opersys.processes.ProcessUtils;
import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.NotFound;

class FindWith_tasklist {

	public FindWith_tasklist(final Criteria criteria)
	{
		this.criteria = criteria;
	}

	int find() throws NotFound
	{
		try
		{
			return findOrCry();
		}
		catch (final IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	private int extract_pid(final String tasklist) throws NotFound
	{
		final int exeb = StringUtils.indexOfIgnoreCase(tasklist,
				criteria.executable);
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

	private int findOrCry()
			throws IOException, NotFound
	{
		final String tasklist = run_tasklist();
		final int pid = extract_pid(tasklist);
		return pid;
	}

	private String run_tasklist() throws IOException
	{
		final List<String> command = new ArrayList<String>(Arrays.asList(
				"tasklist.exe",
				"/FI",
				"IMAGENAME eq " + criteria.executable));

		criteria.usernameNot
				.ifPresent(usernameNot -> command.addAll(Arrays.asList(
						"/FI",
						"USERNAME ne " + usernameNot.username)));

		return ProcessUtils.processout(new ProcessBuilder(command));
	}

	private final Criteria criteria;

}
