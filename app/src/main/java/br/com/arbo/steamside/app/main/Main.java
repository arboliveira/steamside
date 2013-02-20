package br.com.arbo.steamside.app.main;

public class Main {

	private final ContainerFactory factory;

	public Main(final ContainerFactory factory) {
		this.factory = factory;
	}

	public static void main(final String[] args) throws Exception {
		new Main(new ContainerFactory(new JettyCallback())).start();
	}

	public void start() {
		factory.newContainer().start();
	}
}
