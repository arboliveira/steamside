export function toGame(game) {
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
