import {Tag} from "#steamside/data-tag";

export type Game = {
	appid: string,
			name: string,
			link: string,
			image: string,
			store: string,
			unavailable: boolean,
			tags: Array<Tag>,
}

export function toGame(game: any): Game {
	return {
		appid: game.appid,
		name: game.name,
		link: game.link,
		image: game.image,
		store: game.store,
		unavailable: game.unavailable === 'Y',
		tags: game.tags,
	};
}
