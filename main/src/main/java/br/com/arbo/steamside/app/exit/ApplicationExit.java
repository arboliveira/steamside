package br.com.arbo.steamside.app.exit;

import javax.inject.Inject;

import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import br.com.arbo.steamside.exit.Exit;

public class ApplicationExit implements Exit {

	@Inject
	public ApplicationExit(ApplicationContext application)
	{
		this.application = application;
	}

	@Override
	public void exit()
	{
		SpringApplication.exit(application);
	}

	private final ApplicationContext application;

}