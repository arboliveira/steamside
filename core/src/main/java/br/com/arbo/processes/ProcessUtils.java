package br.com.arbo.processes;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;

public class ProcessUtils {

	public static String processout(final String... command)
			throws IOException {
		final ProcessBuilder processBuilder = new ProcessBuilder(command);
		final Process process = processBuilder.start();
		final InputStream is = process.getInputStream();
		final StringWriter sw = new StringWriter();
		IOUtils.copy(is, sw);
		return sw.toString();
	}
}