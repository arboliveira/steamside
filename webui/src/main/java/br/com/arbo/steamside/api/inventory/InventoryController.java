package br.com.arbo.steamside.api.inventory;

import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppDTO_json;
import br.com.arbo.steamside.collections.Tag;
import br.com.arbo.steamside.collections.TagsQueries;
import br.com.arbo.steamside.collections.system.Everything;
import br.com.arbo.steamside.collections.system.Uncollected;
import br.com.arbo.steamside.steam.client.library.Library;

@RestController
@RequestMapping("inventory")
public class InventoryController
{

	@RequestMapping(value = "owned.json")
	public List<AppDTO> owned()
	{
		Stream< ? extends Tag> apps = new Everything(library).apps();
		return jsonable(apps);
	}

	@RequestMapping(value = "tagless.json")
	public List<AppDTO> tagless()
	{
		Stream< ? extends Tag> apps = new Uncollected(library, tags).apps();
		return jsonable(apps);
	}

	private List<AppDTO> jsonable(Stream< ? extends Tag> apps)
	{
		return new AppDTO_json(
			tags, library, apiAppSettings.limit()).jsonable(apps);
	}

	@Inject
	public Library library;
	@Inject
	public TagsQueries tags;
	@Inject
	public br.com.arbo.steamside.api.app.AppSettings apiAppSettings;

}
