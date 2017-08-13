package br.com.arbo.steamside.kids;

import java.util.Optional;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;

public class FromUsername implements KidsModeDetector
{

	@Override
	public Optional<KidsMode> kidsMode()
	{
		return findKid().map(WithKid::new);
	}

	private Optional<Kid> findKid()
	{
		try
		{
			return Optional.of(kids.find(user));
		}
		catch (NotFound e)
		{
			return Optional.empty();
		}
	}

	@Inject
	public FromUsername(final User user, final Kids kids)
	{
		this.user = user;
		this.kids = kids;
	}

	private final Kids kids;

	private final User user;

	public static class WithKid implements KidsMode
	{

		@Override
		public Kid kid()
		{
			return kid;
		}

		public WithKid(Kid kid)
		{
			this.kid = kid;
		}

		private final Kid kid;

	}

}
