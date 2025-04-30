import {fromUrl} from "#steamside/data-url.js";

export type SessionData = {
	backoff: boolean,
	versionOfSteamside: string,
	gamesOwned: number,
	userName: string,
	kidsMode: boolean,
	executable: string,
}

export async function fetchSessionData(): Promise<SessionData>
{
	const location = "api/session/session.json";
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	return {
		backoff: booleanNeed(json.backoff), // only present in mock json
		versionOfSteamside: json.versionOfSteamside,
		gamesOwned: json.gamesOwned,
		userName: json.userName,
		kidsMode: booleanMust(json.kidsMode),
		executable: json.executable
	}
}

function booleanNeed(value: unknown): boolean {
	if (value === undefined) return false;
	return booleanMust(value);
}

function booleanMust(value: unknown): boolean
{
	if (value === true) return true;
	if (value === false) return false;
	throw new Error('Value must be boolean true or false');
}
