package br.com.arbo.steamside.api.kids;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.arbo.opersys.username.User;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidCheck;
import br.com.arbo.steamside.kids.KidCheckImpl;
import br.com.arbo.steamside.kids.KidName;
import br.com.arbo.steamside.kids.KidsData;
import br.com.arbo.steamside.kids.NotFound;

public class KidsController_kids_json implements KidsController_kids
{

	@Override
	public KidDTO add(KidDTO payload)
	{
		Kid in = payload.toKid();
		KidCheck with = check(in);
		data.add(with);
		return refind(in);
	}

	@Override
	public KidDTO delete(String id) throws NotFound
	{
		Kid was = data.find(new KidName(id));
		data.delete(was);
		return KidDTO.valueOf(was);
	}

	@Override
	public List<KidDTO> jsonable()
	{
		return data.all().map(KidDTO::valueOf).collect(Collectors.toList());
	}

	@Override
	public KidDTO update(String id, KidDTO payload)
	{
		Kid in = payload.toKid();
		Kid target = data.find(new KidName(id));
		KidCheck with = check(in);
		data.update(target, with);
		return refind(in);
	}

	private KidCheckImpl check(Kid in)
	{
		return new KidCheckImpl(in, user);
	}

	private KidDTO refind(Kid kid)
	{
		return KidDTO.valueOf(data.find(kid.getUser()));
	}

	@Inject
	public KidsData data;

	@Inject
	public User user;
}
