package br.com.arbo.steamside.kids;

import java.util.Arrays;
import java.util.List;

import br.com.arbo.steamside.opersys.username.User;

public class FromUsername implements KidsMode {

	final List<String> kids = Arrays.asList("kid");

	@Override
	public boolean isKidsModeOn() {
		final String username = user.username();
		// TODO Read from settings of the given username
		return kids.contains(username);
	}

	public FromUsername(final User user) {
		this.user = user;
	}

	@Override
	public String getCategoryAllowedToBeSeen() {
		// TODO Read from settings of the given username
		return "favorite";
	}

	private final User user;
}
