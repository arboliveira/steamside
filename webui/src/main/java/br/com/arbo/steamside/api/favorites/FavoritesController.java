package br.com.arbo.steamside.api.favorites;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.collections.CollectionI;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.collections.TagsData;
import br.com.arbo.steamside.types.CollectionName;

@RestController
@RequestMapping("favorites")
public class FavoritesController
{

	@RequestMapping("favorites.json")
	public List<AppDTO> favorites()
	{
		return favorites.jsonable();
	}

	@RequestMapping(value = "set/{name}")
	public void set(
		@PathVariable String name)
	{
		CollectionsData c = tags.collections();
		CollectionI in = c.find(new CollectionName(name));
		c.favorite(in);
	}

	@Inject
	public FavoritesController_favorites favorites;

	@Inject
	private TagsData tags;
}
