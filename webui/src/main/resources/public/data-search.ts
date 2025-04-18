import {Game, toGame} from "#steamside/data-game.js";
import {fromUrl} from "#steamside/data-url.js";

export async function fetchSearchData(query: string): Promise<Game[]> {
	const location = `api/search/${encodeURIComponent(query)}/search.json`;
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	return json.map((game: any) => toGame(game));
}
