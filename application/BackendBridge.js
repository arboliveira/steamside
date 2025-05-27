import { imagineDryRun } from "#steamside/data-offline-mode.js";
export class BackendBridge {
    constructor(options) {
        this.options = options;
        this.state = this.options?.dryRun ? new MockState(this.options) : new SeriousState();
        this.inputLastRequested = undefined;
    }
    async fetch(input, init) {
        this.inputLastRequested = input;
        return await this.state.fetch(input, init);
    }
    getInputLastRequested() {
        return this.inputLastRequested;
    }
    quit() {
        this.state.quit();
    }
}
class SeriousState {
    async fetch(input, init) {
        const response = await fetch(input, init);
        if (!response.ok) {
            throw new Error(boom(`${response.status} ${response.statusText}: ${input}`));
        }
    }
    quit() { }
}
class MockState {
    constructor(options) {
        this.options = options;
        this.let_it_go = false;
    }
    async fetch(input, init) {
        if (this.options.broken) {
            throw new Error(boom(imagineDryRun({ dryRun: true, imagine: `something went wrong`, url: input, requestInit: init })));
        }
        if (this.options.sleep) {
            await new Promise(resolve => setTimeout(resolve, this.options.sleep));
        }
        if (this.options.forever) {
            while (!this.let_it_go) {
                await new Promise(resolve => setTimeout(resolve, 100));
            }
        }
    }
    quit() {
        this.let_it_go = true;
    }
}
function boom(s) {
    return `💥 ${s}`;
}
//# sourceMappingURL=BackendBridge.js.map