import { toGame } from "#steamside/data-game.js";
import { fromUrl } from "#steamside/data-url.js";
export async function fetchContinuesData() {
    const location = 'api/continues/continues.json';
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    return json.map((game) => toGame(game));
}
//# sourceMappingURL=data-continues.js.map