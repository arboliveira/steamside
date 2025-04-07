export async function fetchOfflineModeData(): Promise<boolean> {
	// FIXME decouple from session: api/offline-mode.json never served from back-end

	const location = "api/session/session.json";
	const sameHostAndPort = import.meta.resolve('./'+location);
	const response = await fetch(sameHostAndPort);
	const json = await response.json();

	return json.backoff === undefined // only present in mock json
		? false
		: strictlyBoolean(json.backoff);
}

function strictlyBoolean(value: boolean)
{
	if (value === true) return true;
	if (value === false) return false;
	throw new Error('Value must be boolean true or false');
}
