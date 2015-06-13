import {Game, toGame} from "#steamside/data-game.js";
import {fromUrl} from "#steamside/data-url.js";

export async function fetchContinuesData(): Promise<Game[]>
{
	const location = 'api/continues/continues.json';
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	return json.map((game: object) => toGame(game));
}
