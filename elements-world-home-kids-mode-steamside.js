import { Customary, CustomaryElement } from "#customary";
import { WorldHomeFavoritesSegmentElement } from "#steamside/elements-world-home-favorites-segment-steamside.js";
export class WorldHomeKidsModeElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-world-home-kids-mode-steamside',
        config: {
            define: {
                fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular",
            },
        },
        hooks: {
            requires: [WorldHomeFavoritesSegmentElement],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
            },
        }
    }; }
    async #on_connected() {
        const classList = document.body.classList;
        classList.remove("steamside-body-background");
        classList.add("steamside-kids-body-background");
    }
}
Customary.declare(WorldHomeKidsModeElement);
//# sourceMappingURL=elements-world-home-kids-mode-steamside.js.map