import { Backend } from "#steamside/data-backend.js";
export class MockBackend extends Backend {
    constructor() {
        super(...arguments);
        this.well_done_fetchBackend = false;
        this.fetchBackend_url = '';
    }
    async fetchSessionDataAndDisableBackendIfOffline() { }
    async fetchBackend({ url, requestInit }) {
        this.fetchBackend_url = url;
        while (!this.well_done_fetchBackend) {
            await new Promise(resolve => setTimeout(resolve, 100));
        }
        return {};
    }
}
//# sourceMappingURL=test-mock-data-backend.js.map