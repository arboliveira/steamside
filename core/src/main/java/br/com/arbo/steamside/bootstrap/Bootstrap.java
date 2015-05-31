package br.com.arbo.steamside.bootstrap;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface Bootstrap
{
	void addObserver(EventObserver observer);

	void fireEvent(Event event);

	<T> Future<T> whenWired(Callable<T> task);

	Future<Bootstrap> whenWired(Runnable task);
}
