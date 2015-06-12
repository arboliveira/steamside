package br.com.arbo.steamside.app.main;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import br.com.arbo.org.springframework.boot.builder.Sources;
import br.com.arbo.steamside.api.app.AppDTO;
import br.com.arbo.steamside.api.app.Image;
import br.com.arbo.steamside.api.favorites.FavoritesController_favorites;
import br.com.arbo.steamside.kids.Kid;
import br.com.arbo.steamside.kids.KidsMode;
import br.com.arbo.steamside.steam.client.types.AppId;
import br.com.arbo.steamside.types.CollectionName;

public class KidsModeActive
{

	public static Sources customize(Sources s)
	{
		s.replaceWithSingleton(KidsMode.class, mockKidsModeActive());
		s.replaceWithSingleton(
			FavoritesController_favorites.class, mockFavorites());
		return s;
	}

	static KidsMode mockKidsModeActive()
	{
		Kid kid = mock(Kid.class);
		doReturn(new CollectionName("Kids")).when(kid).getCollection();
		KidsMode kidsMode = mock(KidsMode.class);
		doReturn(kid).when(kidsMode).kid();
		return kidsMode;
	}

	private static void add(List<AppDTO> apps, AppDTO appDTO)
	{
		appDTO.image = Image.image(new AppId(appDTO.appid));
		apps.add(appDTO);
	}

	private static FavoritesController_favorites mockFavorites()
	{
		List<AppDTO> apps = new ArrayList<>();
		populate(apps);
		FavoritesController_favorites mock =
			mock(FavoritesController_favorites.class);
		doReturn(apps).when(mock).jsonable();
		return mock;
	}

	private static void populate(List<AppDTO> apps)
	{
		// @formatter:off
		add(apps, new AppDTO() {{
			name = "Windosill";
			appid = "37600";
		}});
		add(apps, new AppDTO() {{
			name = "VVVVVV";
			appid = "70300";
		}});
		add(apps, new AppDTO() {{
			name = "Scribblenauts Unlimited";
			appid = "218680";
		}});
		add(apps, new AppDTO() {{
			name = "FEZ";
			appid = "224760";
		}});
		add(apps, new AppDTO() {{
			name = "ibb & obb";
			appid = "95400";
		}});
		add(apps, new AppDTO() {{
			name = "LEGO MARVEL Super Heroes";
			appid = "249130";
		}});
		add(apps, new AppDTO() {{
			name = "Super Toy Cars";
			appid = "116100";
		}});
		add(apps, new AppDTO() {{
			name = "Psychonauts";
			appid = "3830";
		}});
		// @formatter:on
	}

}