package br.com.arbo.steamside.app.embedded;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Welcome {

	@RequestMapping("/")
	public static String index()
	{
		return "forward:/Steamside.html";
	}

}
