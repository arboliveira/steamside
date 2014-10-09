package br.com.arbo.steamside.cloud;

import br.com.arbo.steamside.cloud.dontpad.Dontpad;
import br.com.arbo.steamside.cloud.dontpad.DontpadSettingsFromLocalSettings;

public class ExampleDontpad {

	public static void main(String[] args)
	{
		System.out.println(
				new Cloud(new Dontpad(new DontpadSettingsFromLocalSettings())).download()
				);
	}
}
