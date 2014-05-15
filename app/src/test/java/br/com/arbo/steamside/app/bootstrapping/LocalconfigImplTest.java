package br.com.arbo.steamside.app.bootstrapping;

import java.util.concurrent.Executor;

import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class LocalconfigImplTest {

	@Test
	public void assemble__enqueuesParse() {
		new LocalconfigImpl(executor).assemble();
		Mockito.verify(executor).execute(Matchers.isA(LocalconfigParse.class));
	}

	@Mock
	Executor executor;

	{
		MockitoAnnotations.initMocks(this);
	}
}
