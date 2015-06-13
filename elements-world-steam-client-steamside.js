import { Customary, CustomaryElement } from "#customary";
import { Backend } from "#steamside/data-backend.js";
import { pop_toast } from "#steamside/vfx-toaster.js";
export class WorldSteamClientElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.statusBackend = new StatusBackend();
        this.backend = new Backend();
        this.steamBrowserProtocolBackend = new SteamBrowserProtocolBackend(this.backend);
    }
    static { this.customary = {
        name: 'elements-world-steam-client-steamside',
        config: {
            state: [
                'statusVisible', 'statusText',
                'anotherUserVisible',
                'buttonVisible', 'buttonText'
            ],
        },
        hooks: {
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
            },
            events: {
                '.button-steam-browser-protocol': (el, e) => el.#buttonSteamBrowserProtocolClicked(e),
            },
        }
    }; }
    async #on_connected() {
        await this.backend.fetchSessionDataAndDisableBackendIfOffline();
        const status = await this.statusBackend.fetchStatus();
        let statusVisible = false;
        let statusText = '';
        let anotherVisible = false;
        let buttonText = 'Open Steam Client';
        if (status.running) {
            if (status.here) {
                statusVisible = true;
                statusText = 'Steam is running.';
            }
            else {
                anotherVisible = true;
                buttonText = 'Reopen Steam Client here';
            }
        }
        else {
            statusVisible = true;
            statusText = 'Steam is not running.';
        }
        this.statusVisible = statusVisible;
        this.anotherUserVisible = anotherVisible;
        this.buttonVisible = true;
        this.buttonText = buttonText;
        this.statusText = statusText;
    }
    async #buttonSteamBrowserProtocolClicked(e) {
        e.preventDefault();
        const htmlLinkElement = e.target;
        const aUrl = htmlLinkElement.getAttribute("href");
        try {
            await this.steamBrowserProtocolBackend.sendCommand(aUrl);
        }
        catch (error) {
            pop_toast({
                error: error,
                target: htmlLinkElement,
                offline_imagine_spot: 'Steam launching',
            });
        }
    }
}
Customary.declare(WorldSteamClientElement);
function strictlyBoolean(value) {
    if (value === true)
        return true;
    if (value === false)
        return false;
    throw new Error('Value must be boolean true or false');
}
export class StatusBackend {
    async fetchStatus() {
        const location = "./api/steamclient/status.json";
        const response = await fetch(import.meta.resolve(location));
        const json = await response.json();
        return {
            running: strictlyBoolean(json.running),
            here: strictlyBoolean(json.here),
        };
    }
}
export class SteamBrowserProtocolBackend {
    constructor(backend) {
        this.backend = backend;
    }
    async sendCommand(aUrl) {
        await this.backend.fetchBackend({ url: aUrl });
    }
}
//# sourceMappingURL=elements-world-steam-client-steamside.js.map