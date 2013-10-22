package br.com.arbo.steamside.kids;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import br.com.arbo.steamside.opersys.username.User;

public class FromUsername implements KidsMode {

	@Override
	public boolean isKidsModeOn() {
		final String username = user.username();
		// TODO Read from settings of the given username
		return kids.contains(username);
	}

	@Override
	public String getCategoryAllowedToBeSeen() {
		// TODO Read from settings of the given username
		return "favorite";
	}

	@Inject
	public FromUsername(final User user) {
		this.user = user;
	}

	final List<String> kids = Arrays.asList("kid");

	private final User user;
}
