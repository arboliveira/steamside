package br.com.arbo.steamside.vdf;

public interface KeyValueVisitor {

	class Finished extends Throwable {
		//
	}

	void onKeyValue(String k, String v) throws Finished;

	void onSubRegion(String k, RegionImpl r) throws Finished;
}