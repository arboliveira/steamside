import {fromUrl} from "#steamside/data-url.js";

export type CloudSettings = {
	cloudEnabled: boolean;
	cloudSyncedLocation: string;
}

export async function fetchCloudSettingsData(): Promise<CloudSettings>
{
	const location = "api/cloud/cloud.json";
	const response = await fetch(fromUrl(location));
	const json = await response.json();
	return {
		cloudEnabled: strictlyBoolean(json.cloud),
		cloudSyncedLocation: json.cloudSyncedLocation,
	}
}

function strictlyBoolean(value: boolean)
{
	if (value === true) return true;
	if (value === false) return false;
	throw new Error('Value must be boolean true or false');
}
