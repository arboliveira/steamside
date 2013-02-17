/*
 * Created on 2013-02-17
 */
package br.com.arbo.steamside.spike;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang3.SystemUtils;
import org.jvnet.winp.WinProcess;
import org.jvnet.winp.WinpException;

public class KillSteam {

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final String myusername = SystemUtils.USER_NAME;
		System.out.println();
		final Iterable<WinProcess> all = WinProcess.all();
		for (final WinProcess p : all)
			try {
				final String commandLine = p.getCommandLine();
				if (commandLine.contains("steam.exe")) {
					System.out.println(commandLine);
					final TreeMap<String, String> env = p
							.getEnvironmentVariables();
					final String steamusername = env.get("USERNAME");
					if (!steamusername.equals(myusername))
						p.killRecursively();
					if (false) {
						final Set<Entry<String, String>> set = env
								.entrySet();
						for (final Entry<String, String> entry : set)
							System.out.println(entry.getKey() + "=" +
									entry.getValue());
					}
				}
			} catch (final WinpException e) {
				//System.out.println(e);
			}
	}
}
