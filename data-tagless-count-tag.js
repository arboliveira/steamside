import { toTag } from "#steamside/data-tag.js";
import { fromUrl } from "#steamside/data-url.js";
export async function fetchTaglessCountData() {
    const location = "api/inventory/tagless-count.json";
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    const tag = toTag(json);
    tag.builtin = true;
    return tag;
}
//# sourceMappingURL=data-tagless-count-tag.js.map