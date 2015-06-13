import { toGame } from "#steamside/data-game.js";
import { fromUrl } from "#steamside/data-url.js";
export async function fetchFavoritesData() {
    const location = './api/favorites/favorites.json';
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    return json.map((game) => toGame(game));
}
//# sourceMappingURL=data-favorites.js.map