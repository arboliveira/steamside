package br.com.arbo.steamside.api.collection;

import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.apps.MissingFrom_appinfo_vdf;
import br.com.arbo.steamside.apps.NotFound;
import br.com.arbo.steamside.collections.CollectionImpl;
import br.com.arbo.steamside.collections.CollectionsData;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

@Controller
@RequestMapping("collection")
public class CollectionController {

	@Inject
	public CollectionController(Library library, CollectionsData data) {
		this.library = library;
		this.data = data;
	}

	@RequestMapping(value = "{name}/add/{appid}")
	@ResponseBody
	public void add(@PathVariable final @NonNull String name,
			@PathVariable final @NonNull String appid)
		throws
		br.com.arbo.steamside.data.collections.NotFound
	{
		data.tag(new CollectionName(name), new AppId(appid));
	}

	@RequestMapping(value = "{name}/create")
	@ResponseBody
	public void create(@PathVariable final @NonNull String name)
	{
		data.add(new CollectionImpl(new CollectionName(name)));
	}

	@RequestMapping(value = "collection.json", params = "name")
	@ResponseBody
	public List<AppDTO> json(@RequestParam final String name)
		throws
		br.com.arbo.steamside.data.collections.NotFound
	{
		final List<AppDTO> result = new LinkedList<AppDTO>();
		data.find(new CollectionName(name)).apps().forEach(
				app -> add(result, app.appid())
				);
		return result;
	}

	void add(final List<AppDTO> result, final AppId appid)
	{
		try {
			result.add(toDTO(appid));
		}
		catch (final MissingFrom_appinfo_vdf e) {
			return;
		}
		catch (NotFound e) {
			return;
		}
	}

	private AppDTO toDTO(final AppId appid)
		throws MissingFrom_appinfo_vdf, NotFound
	{
		return AppDTO.valueOf(appid, this.library);
	}

	private final Library library;

	private final CollectionsData data;

}
