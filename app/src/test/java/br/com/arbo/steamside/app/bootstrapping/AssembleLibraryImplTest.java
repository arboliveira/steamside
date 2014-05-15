package br.com.arbo.steamside.app.bootstrapping;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AssembleLibraryImplTest {

	@Mock
	private Localconfig localconfig;

	@Test
	public void assemble__triggersAll() {
		AssembleLibraryImpl impl = new AssembleLibraryImpl(localconfig);
		impl.assemble();
		verify(localconfig).assemble();
	}

	{
		MockitoAnnotations.initMocks(this);
	}
}
