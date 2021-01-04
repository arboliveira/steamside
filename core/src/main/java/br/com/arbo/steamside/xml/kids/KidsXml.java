package br.com.arbo.steamside.xml.kids;

import java.util.LinkedList;

import br.com.arbo.steamside.data.collections.Duplicate;
import br.com.arbo.steamside.kids.InMemoryKids;
import br.com.arbo.steamside.kids.KidCheck;
import br.com.arbo.steamside.kids.KidImpl;

public class KidsXml
{

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
			KidImpl in = xml.toKid();
			KidCheck preapproved = () -> in;
			mem.add(preapproved);
		}

		InMemoryKids mem;
	}

	public final LinkedList<KidXml> kid = new LinkedList<>();

}
