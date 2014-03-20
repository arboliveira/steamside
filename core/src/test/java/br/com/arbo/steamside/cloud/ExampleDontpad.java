package br.com.arbo.steamside.cloud;

import br.com.arbo.steamside.cloud.dontpad.Dontpad;

public class ExampleDontpad {

	public static void main(String[] args)
	{
		System.out.println(
				new Cloud(new Dontpad()).download()
				);
	}
}
