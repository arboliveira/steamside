import {Backend} from "#steamside/data-backend.js";

export class MockBackend extends Backend {
	async fetchSessionDataAndDisableBackendIfOffline() {}
	async fetchBackend({url, requestInit}:{url: string, requestInit?: RequestInit}): Promise<Response> {
		this.fetchBackend_url = url;
		while (!this.well_done_fetchBackend) {
			await new Promise(resolve => setTimeout(resolve, 100));
		}
		return {} as Response;
	}
	well_done_fetchBackend: boolean = false;
	fetchBackend_url: string = '';
}
