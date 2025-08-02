import { Customary, CustomaryElement } from "#customary";
import { WorldHomeAdvancedModeElement } from "#steamside/elements-world-home-advanced-mode-steamside.js";
import { WorldHomeKidsModeElement } from "#steamside/elements-world-home-kids-mode-steamside.js";
import { KidsModeRead } from "#steamside/application/modules/kids/KidsModeRead.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
import { kidsMode_from_url } from "#steamside/application/modules/kids/kidsMode_from_url.js";
export class WorldHomeElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-world-home-steamside',
        config: {
            construct: {
                shadowRootDont: true,
            },
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
            },
            changes: {
                '__kidsMode': (el, a) => el.#on_kidsMode_change(a),
            },
            events: [
                {
                    type: KidsModeRead.eventTypeDone,
                    listener: (el, e) => el.#on_KidsModeReadPlease_DONE(e),
                },
            ],
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
    #on_KidsModeReadPlease_DONE(event) {
        this.__kidsMode = event.detail.kidsMode || kidsMode_from_url();
    }
    #on_connected() {
        Skyward.fly(this, { type: KidsModeRead.eventTypePlease });
    }
}
Customary.declare(WorldHomeElement);
//# sourceMappingURL=elements-world-home-steamside.js.map