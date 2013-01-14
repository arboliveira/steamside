package br.com.arbo.steamside.vdf;

interface KeyValueVisitor {

	class Finished extends Throwable {
		//
	}

	void onKeyValue(String k, String v) throws Finished;

	void onSubRegion(String k, Region r) throws Finished;
}