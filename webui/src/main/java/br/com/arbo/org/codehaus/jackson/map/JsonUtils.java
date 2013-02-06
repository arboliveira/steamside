package br.com.arbo.org.codehaus.jackson.map;

import java.io.IOException;
import java.io.OutputStream;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

public class JsonUtils {

	public static String asString(final Object value) {
		final ObjectWriter writer = newWriter();
		try {
			return writer.writeValueAsString(value);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void write(final OutputStream outputStream,
			final Object value) {
		final ObjectWriter writer = newWriter();
		try {
			writer.writeValue(outputStream, value);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	private static ObjectWriter newWriter() {
		final ObjectMapper mapper = new ObjectMapper();
		final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
		return writer;
	}

}