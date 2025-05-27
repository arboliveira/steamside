import { Customary, CustomaryElement } from "#customary";
import { WorldHomeAdvancedModeElement } from "#steamside/elements-world-home-advanced-mode-steamside.js";
import { WorldHomeKidsModeElement } from "#steamside/elements-world-home-kids-mode-steamside.js";
import { SteamsideApplication } from "#steamside/application/SteamsideApplication.js";
import { EventBus } from "#steamside/event-bus/EventBus.js";
export class WorldHomeElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.sky = new SteamsideApplication(new EventBus(this));
    }
    static { this.customary = {
        name: 'elements-world-home-steamside',
        config: {
            state: [
                '__advancedMode_visible',
                '__kidsMode_visible', '__kidsMode',
            ],
        },
        hooks: {
            requires: [
                WorldHomeAdvancedModeElement,
                WorldHomeKidsModeElement
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
                disconnected: el => el.#on_disconnected(),
            },
            changes: {
                '__kidsMode': (el, a) => el.#on_kidsMode_change(a),
            },
            events: [],
        },
    }; }
    #on_kidsMode_change(a) {
        if (a) {
            this.__advancedMode_visible = false;
            this.__kidsMode_visible = true;
        }
        else {
            this.__advancedMode_visible = true;
            this.__kidsMode_visible = false;
        }
    }
    #on_NowPlaying(event) {
        // FIXME dispatch HubRefreshPlease to Continues element so fun is shown
    }
    #on_GameOver(event) {
        // FIXME dispatch HubRefreshPlease to Continues element so fun time is shown
    }
    async #on_connected() {
        this.sky.on_connected();
        const sessionData = await this.sky.on_connected_fetchSessionData();
        this.__kidsMode = sessionData.kidsMode || kidsMode_from_url();
    }
    #on_disconnected() {
        this.sky.on_disconnected();
    }
}
Customary.declare(WorldHomeElement);
function kidsMode_from_url() {
    const kids_mode_param = new URLSearchParams(window.location.search)
        .get('kids');
    return !['false', 'no', '0', ''].includes(kids_mode_param?.toLowerCase() ?? '');
}
//# sourceMappingURL=elements-world-home-steamside.js.map