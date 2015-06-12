package br.com.arbo.steamside.api.steamclient;

import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.UsernameNot;
import br.com.arbo.opersys.processes.seek.windows.WinProcessX;
import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.steam.client.executable.SteamExeFindProcess;

public class StatusDTOBuilder implements SteamClientController_status
{

	@Inject
	public StatusDTOBuilder(User user)
	{
		this.user = user;
	}

	@Override
	public StatusDTO status()
	{
		findThere();
		if (dto.running) return dto;

		findHere();
		if (dto.running) dto.here = true;

		return dto;
	}

	void exe(@SuppressWarnings("unused") WinProcessX exe)
	{
		dto.running = true;
	}

	private void findHere()
	{
		seek(new Criteria());
	}

	private void findThere()
	{
		Criteria criteria = new Criteria();
		criteria.usernameNot = Optional.of(new UsernameNot(user.username()));
		seek(criteria);
	}

	private void seek(final Criteria criteria)
	{
		SteamExeFindProcess.seek(criteria, this::exe);
	}

	private final User user;

	StatusDTO dto = new StatusDTO();
}
