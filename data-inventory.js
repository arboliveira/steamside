import { toGame } from "#steamside/data-game.js";
import { fromUrl } from "#steamside/data-url.js";
import { fetchOwnedInventoryData } from "#steamside/data-owned-inventory.js";
import { fetchTaglessInventoryData } from "#steamside/data-tagless-inventory.js";
export async function fetchInventoryContentsOfTag(tag) {
    if (!tag.builtin) {
        return await fetchInventoryContents(tag.name);
    }
    if (tag.name === 'Tagless so far') {
        return await fetchTaglessInventoryData();
    }
    if (tag.name === 'Owned by you') {
        return await fetchOwnedInventoryData();
    }
    throw new Error();
}
export async function fetchInventoryContents(inventory_name) {
    const location = `api/collection/${encodeURIComponent(inventory_name)}/collection.json`;
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    return json.map((game) => toGame(game));
}
//# sourceMappingURL=data-inventory.js.map