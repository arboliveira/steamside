package br.com.arbo.steamside.app.main;

import br.com.arbo.steamside.app.injection.ContainerWeb;
import br.com.arbo.steamside.app.jetty.WebApplicationContextTweak;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.types.CollectionName;

class KidsModeActive implements WebApplicationContextTweak, KidsMode {

	@Override
	public void tweak(ContainerWeb cx)
	{
		cx.replaceComponent(KidsMode.class, KidsModeActive.class);
	}

	@Override
	public boolean isKidsModeOn()
	{
		return true;
	}

	@Override
	public CollectionName getCollection()
	{
		return new CollectionName("+a-Ongoing");
	}

}