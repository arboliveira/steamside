package br.com.arbo.steamside.api.search;

import java.util.List;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.arbo.steamside.api.app.AppCardDTO;
import br.com.arbo.steamside.api.continues.Continues;
import br.com.arbo.steamside.collections.TagsData;

@RestController
@RequestMapping("search")
public class SearchController
{

	@RequestMapping(value = "search.json", params = { "query", "recent=true" })
	public List<AppCardDTO> recent()
	{
		return this.continues.continues();
	}

	@RequestMapping(value = "{query}/search.json")
	public List<AppCardDTO> search(
			@PathVariable String query)
	{
		return new SearchController_search_json(tagsData).jsonable(query);
	}

	@Inject
	private Continues continues;

	@Inject
	private TagsData tagsData;

}
