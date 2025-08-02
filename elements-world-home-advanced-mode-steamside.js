import { Customary, CustomaryElement } from "#customary";
import { WorldHomeFavoritesSegmentElement } from "#steamside/elements-world-home-favorites-segment-steamside.js";
import { WorldHomeSearchSegmentElement } from "#steamside/elements-world-home-search-segment-steamside.js";
import { WorldHomeSuggestedSegmentsElement } from "#steamside/elements-world-home-suggested-segments-steamside.js";
import { fetchOwnedCountData } from "#steamside/data-owned-count-tag.js";
import { ContinuePlay } from "#steamside/application/modules/continue/ContinuePlay.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
import { AreaContinueElement } from "#steamside/elements/area-continue/area-continue-steamside.js";
import { ContinueName } from "#steamside/application/modules/continue/ContinueName.js";
export class WorldHomeAdvancedModeElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-world-home-advanced-mode-steamside',
        config: {
            construct: { shadowRootDont: true },
            define: {
                fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
            },
            state: [
                'last_played_name_1',
                'last_played_name_2',
                'numberOfGamesOwned',
            ],
        },
        hooks: {
            requires: [
                AreaContinueElement,
                WorldHomeSearchSegmentElement,
                WorldHomeFavoritesSegmentElement,
                WorldHomeSuggestedSegmentsElement,
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
            },
            events: [
                {
                    type: ContinuePlay.eventTypePlease,
                    listener: (el, event) => el.#on_asked_continue_game(event),
                },
                {
                    type: ContinueName.eventTypeChanged,
                    listener: (el, event) => el.#on_changed_continue_name(event),
                },
            ],
        }
    }; }
    #on_changed_continue_name(event) {
        if (event.detail.lastPlayed === 1) {
            this.last_played_name_1 = event.detail.name ?? '';
        }
        if (event.detail.lastPlayed === 2) {
            this.last_played_name_2 = event.detail.name ?? '';
        }
    }
    #on_asked_continue_game(event) {
        const continueArea = this.#getContinueArea();
        Skyward.stage(event, continueArea, event);
    }
    #getContinueArea() {
        return this.renderRoot
            .querySelector("area-continue-steamside");
    }
    async #on_connected() {
        const classList = document.body.classList;
        classList.add("steamside-body-background");
        const ownedCount = await fetchOwnedCountData();
        this.numberOfGamesOwned = ownedCount.count;
    }
}
Customary.declare(WorldHomeAdvancedModeElement);
//# sourceMappingURL=elements-world-home-advanced-mode-steamside.js.map