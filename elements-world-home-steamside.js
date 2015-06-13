import { Customary, CustomaryElement } from "#customary";
import { fetchKidsModeData } from "#steamside/data-kids-mode.js";
import { WorldHomeAdvancedModeElement } from "#steamside/elements-world-home-advanced-mode-steamside.js";
import { WorldHomeKidsModeElement } from "#steamside/elements-world-home-kids-mode-steamside.js";
export class WorldHomeElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-world-home-steamside',
        config: {
            attributes: [],
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
        }
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
    async #on_connected() {
        this.__kidsMode = await fetchKidsModeData();
    }
}
Customary.declare(WorldHomeElement);
//# sourceMappingURL=elements-world-home-steamside.js.map