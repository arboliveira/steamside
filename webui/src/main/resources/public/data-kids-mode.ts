import {fetchSessionData} from "#steamside/data-session.js";

export async function fetchKidsModeData(): Promise<boolean> {
	const sessionData = await fetchSessionData();

	return sessionData.kidsMode;
}
