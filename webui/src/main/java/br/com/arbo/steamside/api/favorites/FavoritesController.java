package br.com.arbo.steamside.api.favorites;

import java.util.List;

import javax.inject.Inject;

import org.eclipse.jdt.annotation.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.AppsDTO;
import br.com.arbo.steamside.apps.App;
import br.com.arbo.steamside.favorites.Favorites;
import br.com.arbo.steamside.library.Library;

@Controller
@RequestMapping("favorites")
public class FavoritesController {

	@RequestMapping("favorites.json")
	@ResponseBody
	public List<AppDTO> favorites()
	{
		return new AppsDTO(
				library.allApps().filter(favorites).map(App::appid), library).jsonable();
	}

	@Inject
	@NonNull
	private Favorites favorites;
	@Inject
	private Library library;
}
