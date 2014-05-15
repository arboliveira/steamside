package br.com.arbo.steamside.app.bootstrapping;

public class AssembleLibraryImpl implements AssembleLibrary {

	private Localconfig localconfig;

	public AssembleLibraryImpl(Localconfig localconfig) {
		this.localconfig = localconfig;
	}

	@Override
	public void assemble() {
		localconfig.assemble();
	}

}
