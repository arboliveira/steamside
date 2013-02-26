package br.com.arbo.steamside.vdf;

public interface Apps {

	App app(String appid) throws NotFound;

	void accept(final Visitor visitor);

	public interface Visitor {

		void each(String appid);
	}

}
