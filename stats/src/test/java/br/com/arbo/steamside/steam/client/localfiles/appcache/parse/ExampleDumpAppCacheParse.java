package br.com.arbo.steamside.steam.client.localfiles.appcache.parse;

import br.com.arbo.steamside.steam.client.localfiles.appcache.DumpAppCacheParse;

public class ExampleDumpAppCacheParse
{

	public static void main(final String[] args)
	{
		new DumpAppCacheParse().dump(System.out::println);
	}

}
