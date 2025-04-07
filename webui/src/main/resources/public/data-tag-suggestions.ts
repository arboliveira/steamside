import {Tag, toTag} from "#steamside/data-tag.js";
import {fromUrl} from "#steamside/data-url.js";

export async function fetchTagSuggestionsData(): Promise<Tag[]> {
	const location = './api/collection/tag-suggestions.json';
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	return json.map((tag: any) => toTag(tag));
}
