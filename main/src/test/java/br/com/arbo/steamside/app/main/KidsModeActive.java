package br.com.arbo.steamside.app.main;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.types.CollectionName;

public class KidsModeActive {

	public static Sources customize(Sources s)
	{
		return s.replaceWithSingleton(KidsMode.class, mockKidsModeActive());
	}

	private static KidsMode mockKidsModeActive()
	{
		Kid kid = mock(Kid.class);
		doReturn(new CollectionName("+a-Ongoing")).when(kid).getCollection();
		KidsMode kidsMode = mock(KidsMode.class);
		doReturn(kid).when(kidsMode).kid();
		return kidsMode;
	}

}