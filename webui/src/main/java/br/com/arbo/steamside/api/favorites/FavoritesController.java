package br.com.arbo.steamside.api.favorites;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.apps.Apps.AppVisitor;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.json.app.AppDTO;
import br.com.arbo.steamside.json.appcollection.ToDTOAppVisitor;
import br.com.arbo.steamside.json.appcollection.ToDTOAppVisitor.Full;
import br.com.arbo.steamside.library.Library;
import br.com.arbo.steamside.steam.client.localfiles.appcache.inmemory.InMemory_appinfo_vdf;

@Controller
@RequestMapping("favorites")
public class FavoritesController {

	@RequestMapping("favorites.json")
	@ResponseBody
	public List<AppDTO> favorites() {
		final ToDTOAppVisitor visitor = new ToDTOAppVisitor(appinfo);
		try {
			from_accept(visitor);
		} catch (final Full full) {
			// All right!
		}
		return new ArrayList<AppDTO>(visitor.collection);
	}

	@SuppressWarnings("null")
	private void from_accept(@NonNull final AppVisitor visitor) {
		@NonNull
		final Favorites filter = favorites;
		library.accept(filter, visitor);
	}

	@Inject
	@NonNull
	private Favorites favorites;
	@Inject
	private InMemory_appinfo_vdf appinfo;
	@Inject
	private Library library;
}
