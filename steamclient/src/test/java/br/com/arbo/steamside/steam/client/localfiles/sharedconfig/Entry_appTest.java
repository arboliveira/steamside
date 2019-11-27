package br.com.arbo.steamside.steam.client.localfiles.sharedconfig;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import br.com.arbo.steamside.steam.client.localfiles.sharedconfig.Entry_app.CategoryVisitor;

public class Entry_appTest {

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void noTags__noProblem() {
		new Entry_app().accept(tagVisitor);
		Mockito.verifyZeroInteractions(tagVisitor);
	}

	@Mock
	CategoryVisitor tagVisitor;

}
