import {fetchOfflineModeData} from "#steamside/data-offline-mode.js";

export class Backend {
	#sessionBackendDisabled: boolean = false;

	async fetchSessionDataAndDisableBackendIfOffline() {
		this.#sessionBackendDisabled = await fetchOfflineModeData();
	}
	
	async fetchBackend({url, requestInit}:{url: string, requestInit?: RequestInit}): Promise<Response> {
		if (this.#sessionBackendDisabled) {
			throw new BackendDisabledError({url, requestInit});
		}
		
		const response = await fetch(url, requestInit);
		if (response.status === 200) {
			return response;
		}
		throw new BackendFetchError({url, response});
	}
}

export class BackendFetchError extends Error {
	url: string;
	response: Response;
	constructor({url, response}:{url: string, response: Response}) {
		super("Failed to fetch: " + url);
		this.url = url;
		this.response = response;
	}
}

export class BackendDisabledError extends Error {
	url: string;
	requestInit: RequestInit | undefined;
	constructor({url, requestInit}:{url: string, requestInit?: RequestInit}) {
		// FIXME include requestInit in the message, for console?
		super("Offline mode! Skipping: \n\n" + url);
		this.url = url;
		this.requestInit = requestInit;
	}
}
