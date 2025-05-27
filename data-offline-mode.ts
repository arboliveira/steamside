import {fetchSessionData} from "#steamside/data-session.js";

export async function fetchOfflineModeData(): Promise<boolean>
{
	const sessionData = await fetchSessionData();

	return sessionData.backoff;
}

export function imagineDryRun(
	{dryRun, imagine, url, requestInit}: {
		dryRun?: boolean,
		imagine: string,
		url: RequestInfo | URL,
		requestInit?: RequestInit,
	}
): string | undefined
{
	if (!dryRun) {
		return undefined;
	}
	const clarification = `Dry run! 😴 Imagine ${imagine}... `;

	const requestInitString = requestInit
		? JSON.stringify(requestInit)
		: '';

	return `${clarification} 🌬️ ${url}${requestInitString}`;
}