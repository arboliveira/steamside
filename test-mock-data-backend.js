export class MockBackend {
    constructor() {
        this.played_url = '';
        this.let_it_go = false;
    }
    async fetch({ url }) {
        this.played_url = url;
        while (!this.let_it_go) {
            await new Promise(resolve => setTimeout(resolve, 100));
        }
    }
}
//# sourceMappingURL=test-mock-data-backend.js.map