package br.com.arbo.steamside.app.bootstrapping;

public class BootstrappingImpl implements Bootstrapping {

	private AssembleLibrary assembleLibrary;

	public BootstrappingImpl(AssembleLibrary assembleLibrary) {
		this.assembleLibrary = assembleLibrary;
	}

	@Override
	public void onAppStarted() {
		assembleLibrary.assemble();
	}

}
