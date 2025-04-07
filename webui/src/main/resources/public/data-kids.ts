import {fromUrl} from "#steamside/data-url.js";

export type Kid = {
	id: string;
	name: string;
	user: string;
	inventory: string;
}

export async function fetchKidsData(): Promise<Kid[]> {
	const location = 'api/kids/kids.json';
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	return json.map((entry: any) => toKid(entry));
}

export async function fetchKidData(id: string): Promise<Kid> {
	const location = `api/kids/kid/${id}.json`;
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	return toKid(json);
}

function toKid(json: any): Kid {
	return {
		id: json.id,
		name: json.name,
		user: json.user,
		inventory: json.inventory,
	}
}
