import { fromUrl } from "#steamside/data-url.js";
export async function fetchCloudSettingsData() {
    const location = "api/cloud/cloud.json";
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    return {
        cloudEnabled: strictlyBoolean(json.cloud),
        cloudSyncedLocation: json.cloudSyncedLocation,
    };
}
function strictlyBoolean(value) {
    if (value === true)
        return true;
    if (value === false)
        return false;
    throw new Error('Value must be boolean true or false');
}
//# sourceMappingURL=data-cloud-settings.js.map