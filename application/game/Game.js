export function translateGameToCardView(game) {
    return {
        appid: game.appid,
        name: game.name,
        link: game.link,
        image: game.image,
        store: game.store,
        unavailable: game.unavailable,
        tags: game.tags?.map(tag => translateTagToTagView(tag)),
    };
}
function translateTagToTagView(tag) {
    return {
        tag_name: tag.name,
        tag_url: `./InventoryWorld.html?name=${tag.name}`,
    };
}
//# sourceMappingURL=Game.js.map