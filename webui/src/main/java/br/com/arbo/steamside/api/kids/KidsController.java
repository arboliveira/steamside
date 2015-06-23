package br.com.arbo.steamside.api.kids;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(br.com.arbo.steamside.mapping.Kids.kids)
public class KidsController
{

	@RequestMapping(value = "kids.json")
	public List<KidDTO> kids()
	{
		return kids.jsonable();
	}

	@Inject
	public KidsController_kids kids;

}
