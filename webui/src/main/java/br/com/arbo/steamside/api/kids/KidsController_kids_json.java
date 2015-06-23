package br.com.arbo.steamside.api.kids;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import br.com.arbo.steamside.kids.KidsData;

public class KidsController_kids_json implements KidsController_kids
{

	@Override
	public List<KidDTO> jsonable()
	{
		return data.all().map(KidDTO::valueOf).collect(Collectors.toList());
	}

	@Inject
	public KidsData data;

}
