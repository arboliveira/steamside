import { fetchSessionData } from "#steamside/data-session.js";
export async function fetchOfflineModeData() {
    const sessionData = await fetchSessionData();
    return sessionData.backoff;
}
export function imagineDryRun({ dryRun, imagine, url, requestInit }) {
    if (!dryRun) {
        return undefined;
    }
    const clarification = `Dry run! 😴 Imagine ${imagine}... `;
    const requestInitString = requestInit
        ? JSON.stringify(requestInit)
        : '';
    return `${clarification} 🌬️ ${url}${requestInitString}`;
}
//# sourceMappingURL=data-offline-mode.js.map