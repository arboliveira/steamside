package br.com.arbo.steamside.api.steamclient;

import br.com.arbo.opersys.processes.seek.Criteria;
import br.com.arbo.opersys.processes.seek.UsernameNot;
import br.com.arbo.opersys.processes.seek.windows.WinProcessX;
import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.steam.client.executable.SteamExeFindProcess;

public class StatusDTOBuilder {

	StatusDTOBuilder(User user)
	{
		this.user = user;
	}

	public StatusDTO build()
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
		final Criteria criteria = new Criteria();
		criteria.usernameNot = new UsernameNot(user.username());
		seek(criteria);
	}

	private void seek(final Criteria criteria)
	{
		SteamExeFindProcess.seek(criteria, this::exe);
	}

	private final User user;

	StatusDTO dto = new StatusDTO();
}
