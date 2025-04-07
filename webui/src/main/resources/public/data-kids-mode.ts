export async function fetchKidsModeData(): Promise<boolean> {
	// FIXME decouple from session: api/kids-mode.json

	const location = "api/session/session.json";
	const sameHostAndPort = import.meta.resolve('./'+location);
	const response = await fetch(sameHostAndPort);
	const json = await response.json();

	return strictlyBoolean(json.kidsMode);  // mock session.json exists, read from it
}

function strictlyBoolean(value: boolean)
{
	if (value === true) return true;
	if (value === false) return false;
	throw new Error('Value must be boolean true or false');
}
