package br.com.arbo.steamside.app.bootstrapping;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class BootstrappingImplTest {

	@Mock
	private AssembleLibrary assembleLibrary;

	@Test
	public void onAppStarted__shouldAssembleLibrary() {
		Bootstrapping b = new BootstrappingImpl(assembleLibrary);
		b.onAppStarted();
		Mockito.verify(assembleLibrary).assemble();
	}

	{
		MockitoAnnotations.initMocks(this);
	}
}
