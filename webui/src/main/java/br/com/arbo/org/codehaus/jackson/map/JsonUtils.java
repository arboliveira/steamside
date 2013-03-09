package br.com.arbo.org.codehaus.jackson.map;

import java.io.IOException;
import java.io.InputStream;
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

	public static void write(final OutputStream out, final Object value) {
		final ObjectWriter writer = newWriter();
		try {
			writer.writeValue(out, value);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T fromString(final String s, final Class<T> type) {
		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(s, type);
		} catch (final IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T read(final InputStream in, final Class<T> type) {
		final ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(in, type);
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