package br.com.arbo.steamside.xml.kids;

import br.com.arbo.opersys.username.Username;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidImpl;
import br.com.arbo.steamside.kids.KidName;
import br.com.arbo.steamside.types.CollectionName;

public class KidXml
{

	public static KidXml valueOf(Kid kid)
	{
		KidXml xml = new KidXml();
		xml.name = kid.getName().name;
		xml.collection = kid.getCollection().value;
		xml.username = kid.getUser().username();
		return xml;
	}

	public KidImpl toKid()
	{
		return new KidImpl(
			new KidName(this.name),
			new Username(this.username),
			new CollectionName(collection));
	}

	public String collection;

	public String name;

	public String username;
}
