package br.com.arbo.steamside.app.main;

public class Main {

	public static void main(final String... args) throws Exception
	{
		SpringApplicationBuilderXFactory.newInstance().build().run(args);
	}

}
