import { toTag } from "#steamside/data-tag.js";
import { fromUrl } from "#steamside/data-url.js";
export async function fetchTagSuggestionsData() {
    const location = './api/collection/tag-suggestions.json';
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    return json.map((tag) => toTag(tag));
}
//# sourceMappingURL=data-tag-suggestions.js.map