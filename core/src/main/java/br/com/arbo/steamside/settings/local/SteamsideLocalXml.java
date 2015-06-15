package br.com.arbo.steamside.settings.local;

import javax.xml.bind.annotation.XmlRootElement;

import br.com.arbo.steamside.cloud.dontpad.DontpadAddress;

@XmlRootElement(name = "steamside-local")
public class SteamsideLocalXml
{

	public void dontpad(DontpadAddress d)
	{
		dontpad = d.url();
	}

	public boolean cloud;

	public String dontpad;

}
