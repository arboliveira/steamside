package br.com.arbo.steamside.opersys.username;

import org.apache.commons.lang3.SystemUtils;

public class UsernameFromJava implements Username {

	@Override
	public String username() {
		return SystemUtils.USER_NAME;
	}

}
