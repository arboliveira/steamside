import { Customary, CustomaryElement } from "#customary";
import { WorldHomeFavoritesSegmentElement } from "#steamside/elements-world-home-favorites-segment-steamside.js";
import { GameCardDeckElement } from "#steamside/elements-game-card-deck-steamside.js";
import { WorldHomeSearchSegmentElement } from "#steamside/elements-world-home-search-segment-steamside.js";
import { WorldHomeSuggestedSegmentsElement } from "#steamside/elements-world-home-suggested-segments-steamside.js";
import { TagAGameElement } from "#steamside/elements-tag-a-game-steamside.js";
import { fetchContinuesData } from "#steamside/data-continues.js";
import { fetchOwnedCountData } from "#steamside/data-owned-count-tag.js";
import { GameCardElement_ActionButtonClick_eventName } from "#steamside/elements/game-card/GameCardElement_ActionButtonClick_Event.js";
export class WorldHomeAdvancedModeElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-world-home-advanced-mode-steamside',
        config: {
            define: {
                fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
            },
            state: [
                'continues', 'numberOfGamesOwned',
            ],
            attributes: [
                'last_played_name_1',
                'last_played_name_2',
            ],
        },
        hooks: {
            requires: [
                GameCardDeckElement,
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
                    type: 'WorldHomeSearchSegmentElement:asks-to:continue-game',
                    listener: (el, event) => el.#on_asked_continue_game(event),
                },
                {
                    selector: '.segment',
                    type: GameCardElement_ActionButtonClick_eventName,
                    listener: (el, event) => el.#on_game_card_action_button_click(event),
                },
            ],
        }
    }; }
    async #on_game_card_action_button_click(event) {
        switch (event.detail.action_button) {
            case 'tag':
                new TagAGameElement().showTagAGame({
                    game: event.detail.game,
                    container: event.currentTarget
                });
                break;
        }
    }
    #on_asked_continue_game(event) {
        const { lastPlayed } = event.detail;
        this.#getContinuesDeck().getCardAtIndex(lastPlayed - 1)?.playGame();
    }
    #getContinuesDeck() {
        return this.renderRoot
            .querySelector("#continues-segment")
            .querySelector('elements-game-card-deck-steamside');
    }
    async #on_connected() {
        const classList = document.body.classList;
        classList.add("steamside-body-background");
        this.continues = await fetchContinuesData();
        const ownedCount = await fetchOwnedCountData();
        this.numberOfGamesOwned = ownedCount.count;
        const last_played_1 = this.continues[0];
        const last_played_2 = this.continues[1];
        this.last_played_name_1 = last_played_1 ? last_played_1.name : '';
        this.last_played_name_2 = last_played_2 ? last_played_2.name : '';
    }
}
Customary.declare(WorldHomeAdvancedModeElement);
//# sourceMappingURL=elements-world-home-advanced-mode-steamside.js.map