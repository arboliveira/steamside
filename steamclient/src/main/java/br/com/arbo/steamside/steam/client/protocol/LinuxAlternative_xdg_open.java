package br.com.arbo.steamside.steam.client.protocol;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

/**
 * Observed:
 * <p/>
 * If Steam was already running, 
 * the spawned process quickly returns with output:
 * <pre>
Running Steam on ubuntu 12.04 64-bit
STEAM_RUNTIME is enabled automatically
 * </pre>
 * <p/>
 * If Steam was not already running,
 * Steam starts with lots of output, 
 * and the spawned process blocks us until Steam is closed.
 */
class LinuxAlternative_xdg_open {

	void attempt() {
		final Thread xt = new Thread(this::xt_xdg_open, "xdg-open " + str);
		xt.setDaemon(true);
		xt.start();
		try {
			xt.join(5000);
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	void xt_xdg_open() {
		try {
			run_xdg_open();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void run_xdg_open() throws IOException {
		final Process process = new ProcessBuilder(
				"xdg-open",
				str
				).start();
		IOUtils.copy(process.getInputStream(), System.out);
	}

	LinuxAlternative_xdg_open(final String str) {
		this.str = str;
	}

	private final String str;
}
