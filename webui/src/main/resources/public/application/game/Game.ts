import {Game} from "#steamside/data-game.js";
import {Tag} from "#steamside/data-tag.js";
import {CardView, TagView} from "#steamside/elements-game-card-steamside.js";

export function translateGameToCardView(game: Game): CardView {
    return {
        appid: game.appid,
        name: game.name,
        link: game.link,
        image: game.image,
        store: game.store,
        unavailable: game.unavailable,
        tags: game.tags?.map(tag => translateTagToTagView(tag)),
    }
}

function translateTagToTagView(tag: Tag): TagView {
    return {
        tag_name: tag.name,
        tag_url: `./InventoryWorld.html?name=${tag.name}`,
    };
}
