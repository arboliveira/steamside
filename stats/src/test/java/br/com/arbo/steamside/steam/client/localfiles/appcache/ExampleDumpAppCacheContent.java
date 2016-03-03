package br.com.arbo.steamside.steam.client.localfiles.appcache;

import java.io.IOException;

class ExampleDumpAppCacheContent
{

	public static void main(final String[] args) throws IOException
	{
		new DumpAppCacheContent().dump(System.out::println);
	}

}
