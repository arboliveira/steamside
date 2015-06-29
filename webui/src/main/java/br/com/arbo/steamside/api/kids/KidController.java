package br.com.arbo.steamside.api.kids;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(br.com.arbo.steamside.mapping.Kids.kid)
public class KidController
{

	@RequestMapping(method = RequestMethod.POST)
	public KidDTO add(@RequestBody KidDTO payload)
	{
		return kids.add(payload);
	}

	@RequestMapping(value = "{id}.json",
		method = RequestMethod.DELETE)
	public KidDTO delete(@PathVariable String id)
	{
		return kids.delete(id);
	}

	@RequestMapping(value = "{id}.json",
		method = RequestMethod.PUT)
	public KidDTO update(
		@PathVariable String id,
		@RequestBody KidDTO payload)
	{
		return kids.update(id, payload);
	}

	@Inject
	public KidsController_kids kids;

}
