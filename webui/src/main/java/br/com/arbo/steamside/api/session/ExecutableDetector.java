package br.com.arbo.steamside.api.session;

import org.apache.commons.lang3.StringUtils;

public class ExecutableDetector
{

	public String executable()
	{
		return StringUtils.substringBetween(
			this.getClass().getResource(
				this.getClass().getSimpleName() + ".class").getPath(),
			"file:", "!");
	}

}
