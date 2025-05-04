import { fetchSessionData } from "#steamside/data-session.js";
export async function fetchKidsModeData() {
    const sessionData = await fetchSessionData();
    return sessionData.kidsMode;
}
//# sourceMappingURL=data-kids-mode.js.map