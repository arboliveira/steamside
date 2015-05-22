package br.com.arbo.steamside.settings.local;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "steamside-local")
public class SteamsideLocalXml {

	public boolean cloud;

	public String dontpad;

}
