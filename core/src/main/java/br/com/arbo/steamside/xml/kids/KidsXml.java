package br.com.arbo.steamside.xml.kids;

import java.util.LinkedList;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.kids.KidImpl;
import br.com.arbo.steamside.kids.Kids;

public class KidsXml
{

	public static KidsXml valueOf(Kids kids)
	{
		KidsXml xml = new KidsXml();
		kids.all().map(KidXml::valueOf).forEach(xml.kid::add);
		return xml;
	}

	public void toKidsHome(InMemoryKids mem)
	{
		new ToKidsHome(mem).convert();
	}

	class ToKidsHome
	{

		ToKidsHome(InMemoryKids mem)
		{
			this.mem = mem;
		}

		void convert()
		{
			kid.stream().forEach(this::add);
		}

		private void add(KidXml xml) throws Duplicate
		{
			final KidImpl c = xml.toKid();
			mem.add(c);
		}

		InMemoryKids mem;
	}

	public final LinkedList<KidXml> kid = new LinkedList<>();

}
