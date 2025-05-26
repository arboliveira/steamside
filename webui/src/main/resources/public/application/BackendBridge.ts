import {imagineDryRun} from "#steamside/data-offline-mode.js";

export class BackendBridge implements State
{
    constructor(private readonly options?: Options) {}
    private readonly state: State = this.options?.dryRun ? new MockState(this.options) : new SeriousState();

    async fetch(
        input: RequestInfo | URL, init?: RequestInit
    ): Promise<void>
    {
        this.inputLastRequested = input;
        return await this.state.fetch(input, init);
    }

    getInputLastRequested(): RequestInfo | URL | undefined {
        return this.inputLastRequested;
    }

    quit()
    {
        this.state.quit();
    }

    private inputLastRequested: RequestInfo | URL | undefined = undefined;
}

interface State
{
    fetch(input: RequestInfo | URL, init?: RequestInit): Promise<void>;
    quit(): void;
}

class SeriousState implements State
{
    async fetch(
        input: RequestInfo | URL, init?: RequestInit
    ): Promise<void> {
        const response = await fetch(input, init);
        if (!response.ok) {
            throw new Error(boom(`${response.status} ${response.statusText}: ${input}`));
        }
    }
    quit(): void {}
}

class MockState implements State
{
    constructor(private readonly options: OptionsDryRun) {}

    async fetch(
        input: RequestInfo | URL, init?: RequestInit
    ): Promise<void> {
        if (this.options.broken) {
            throw new Error(boom(imagineDryRun({dryRun: true, imagine: `something went wrong`, url: input, requestInit: init})!));
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
    quit(): void {
        this.let_it_go = true;
    }

    private let_it_go: boolean = false;
}

type Options = undefined | OptionsDryRun;

type OptionsDryRun = {
    dryRun: true,
    sleep?: number,
    forever?: boolean,
    broken?: boolean,
}

function boom(s: string): string {
    return `💥 ${s}`;
}
