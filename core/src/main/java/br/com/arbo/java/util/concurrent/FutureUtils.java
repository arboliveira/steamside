package br.com.arbo.java.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FutureUtils {

	public static <T> T get(final Future<T> state) {
		try {
			return state.get();
		} catch (final InterruptedException e) {
			throw new RuntimeException(e);
		} catch (final ExecutionException e) {
			final Throwable cause = e.getCause();
			if (cause instanceof RuntimeException)
				throw (RuntimeException) cause;
			throw new RuntimeException(e);
		}
	}

}
