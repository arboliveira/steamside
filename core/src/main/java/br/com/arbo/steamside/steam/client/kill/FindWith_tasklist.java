package br.com.arbo.steamside.steam.client.kill;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.jvnet.winp.WinProcess;

class FindWith_tasklist {

	private final String myusername;

	public FindWith_tasklist(final String myusername) {
		this.myusername = myusername;
	}

	WinProcess find() {
		try {
			return findOrCry();
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private WinProcess findOrCry()
			throws IOException {
		final String tasklist = run_tasklist();
		final int pid = extract_pid(tasklist);
		return new WinProcess(pid);
	}

	private static int extract_pid(final String tasklist) {
		final String name = "steam.exe";
		final int exeb = tasklist.indexOf(name);
		if (exeb == -1) throw new NotFound();
		final int exee = exeb + name.length() + 1;
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
		final InputStream is = new ProcessBuilder(
				"tasklist.exe",
				"/FI",
				"USERNAME ne " + myusername,
				"/FI",
				"IMAGENAME eq steam.exe"
				).start()
						.getInputStream();
		final StringWriter sw = new StringWriter();
		IOUtils.copy(is, sw);
		return sw.toString();
	}

}
