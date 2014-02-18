package br.com.arbo.steamside.apps;

public class AppsHomeBox implements AppsHomeFactory {

	private AppsHome home;

	@Override
	public AppsHome get() {
		return home;
	}

	public void set(AppsHome home) {
		this.home = home;
	}

}
