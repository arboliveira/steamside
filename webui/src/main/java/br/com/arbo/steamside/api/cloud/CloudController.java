package br.com.arbo.steamside.api.cloud;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(br.com.arbo.steamside.mapping.Cloud.cloud)
public class CloudController
{

	@RequestMapping(br.com.arbo.steamside.mapping.Cloud.cloud_json)
	public CloudDTO cloud()
	{
		return cloud.jsonable();
	}

	@RequestMapping(
		value = br.com.arbo.steamside.mapping.Cloud.cloud_json,
		method = RequestMethod.POST)
	public CloudDTO cloud_post(@RequestBody CloudDTO payload)
	{
		return cloud.post(payload);
	}

	@Inject
	public CloudController_cloud cloud;

}
