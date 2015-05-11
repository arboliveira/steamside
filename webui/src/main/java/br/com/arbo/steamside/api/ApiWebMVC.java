package br.com.arbo.steamside.api;

import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Import(Api.class)
public class ApiWebMVC {
	//
}
