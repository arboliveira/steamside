import {fromUrl} from "#steamside/data-url.js";
import {toTag} from "#steamside/data-tag.js";

export async function fetchOwnedCountData()
{
	const location = "api/inventory/owned-count.json";
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	const tag = toTag(json);
	tag.builtin = true;
	return tag;
}
