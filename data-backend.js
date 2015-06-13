import { fetchOfflineModeData } from "#steamside/data-offline-mode.js";
export class Backend {
    #sessionBackendDisabled = false;
    async fetchSessionDataAndDisableBackendIfOffline() {
        this.#sessionBackendDisabled = await fetchOfflineModeData();
    }
    async fetchBackend({ url, requestInit }) {
        if (this.#sessionBackendDisabled) {
            throw new BackendDisabledError({ url, requestInit });
        }
        const response = await fetch(url, requestInit);
        if (response.status === 200) {
            return response;
        }
        throw new BackendFetchError({ url, response });
    }
}
export class BackendFetchError extends Error {
    constructor({ url, response }) {
        super("Failed to fetch: " + url);
        this.url = url;
        this.response = response;
    }
}
export class BackendDisabledError extends Error {
    constructor({ url, requestInit }) {
        // FIXME include requestInit in the message, for console?
        super("Offline mode! Skipping: \n\n" + url);
        this.url = url;
        this.requestInit = requestInit;
    }
}
//# sourceMappingURL=data-backend.js.map