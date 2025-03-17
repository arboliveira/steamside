import {toTag} from "#steamside/data-tag.js";
import {fromUrl} from "#steamside/data-url.js";

export async function fetchTagsData()
{
	const location = './api/collection/collections.json';
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	return json.map(tag => toTag(tag));
}
