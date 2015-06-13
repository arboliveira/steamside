import { Customary, CustomaryElement } from "#customary";
import { fetchKidsModeData } from "#steamside/data-kids-mode.js";
export class PageHeaderElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-page-header-steamside',
        config: {
            state: [
                '__advancedMode_visible',
                '__kidsMode_visible', '__kidsMode',
            ],
            define: {
                fontLocations: [
                    'https://fonts.googleapis.com/css?family=Arvo:regular,bold',
                    'https://fonts.googleapis.com/css?family=Jura:regular,bold',
                ],
            },
        },
        hooks: {
            externalLoader: {
                import_meta: import.meta,
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
Customary.declare(PageHeaderElement);
//# sourceMappingURL=elements-page-header-steamside.js.map