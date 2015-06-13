import { fromUrl } from "#steamside/data-url.js";
export async function fetchSessionData() {
    const location = "api/session/session.json";
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    return {
        backoff: json.backoff === undefined // only present in mock json
            ? false
            : strictlyBoolean(json.backoff),
        versionOfSteamside: json.versionOfSteamside,
        gamesOwned: json.gamesOwned,
        userName: json.userName,
        kidsMode: strictlyBoolean(json.kidsMode),
        executable: json.executable
    };
}
function strictlyBoolean(value) {
    if (value === true)
        return true;
    if (value === false)
        return false;
    throw new Error('Value must be boolean true or false');
}
//# sourceMappingURL=data-session.js.map