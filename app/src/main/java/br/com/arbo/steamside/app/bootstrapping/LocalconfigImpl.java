package br.com.arbo.steamside.app.bootstrapping;

import java.util.concurrent.Executor;

public class LocalconfigImpl implements Localconfig {

	private Executor executor;

	public LocalconfigImpl(Executor executor) {
		this.executor = executor;
	}

	@Override
	public void assemble() {
		executor.execute(new LocalconfigParseImpl());
	}

}
