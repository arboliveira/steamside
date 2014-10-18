package br.com.arbo.steamside.app.main;

import org.mockito.Mockito;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.types.CollectionName;

class KidsModeActive implements WebApplicationContextTweak, KidsMode {

	@Override
	public void tweak(ContainerWeb cx)
	{
		cx.replaceComponent(KidsMode.class, KidsModeActive.class);
	}

	@Override
	public Kid kid() throws NotInKidsMode
	{
		return kid;
	}

	private final Kid kid;

	KidsModeActive()
	{
		kid = Mockito.mock(Kid.class);
		Mockito.when(kid.getCollection())
				.thenReturn(new CollectionName("+a-Ongoing"));
	}

}