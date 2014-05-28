package br.com.arbo.steamside.steam.client.vdf;

public interface KeyValueVisitor {

	class Finished extends Throwable {
		//
	}

	public void onKeyValue(String k, String v) throws Finished;

	public void onSubRegion(String k, Region r) throws Finished;
}