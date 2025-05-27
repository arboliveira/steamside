import { Customary, CustomaryElement } from "#customary";
import { GameCardDeckElement } from "#steamside/elements-game-card-deck-steamside.js";
import { TagAGameElement } from "#steamside/elements-tag-a-game-steamside.js";
import { CollectionEditAddGamesCommandBoxElement } from "#steamside/elements-collection-edit-add-games-command-box-steamside.js";
import { fetchSearchData } from "#steamside/data-search.js";
import { CommandPlease } from "#steamside/elements/command-box/CommandPlease.js";
import { CommandAlternatePlease } from "#steamside/elements/command-box/CommandAlternatePlease.js";
import { GameCardTagPlease } from "#steamside/elements/game-card/GameCardTagPlease.js";
export class CollectionEditAddGamesElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-collection-edit-add-games-steamside',
        config: {
            define: {
                fontLocations: [
                    "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
                    'https://fonts.googleapis.com/css?family=Karla:regular,bold'
                ],
            },
            state: [
                'collectionEditSearchResults', 'firstSearchResult'
            ],
        },
        values: {},
        hooks: {
            requires: [
                GameCardDeckElement, CollectionEditAddGamesCommandBoxElement
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            changes: {
                'collectionEditSearchResults': (el, a) => el.#on_collectionEditSearchResults_change(a),
            },
            events: [
                {
                    type: CommandPlease.eventType,
                    listener: (el, e) => el.#on_CommandBoxElement_CommandPlease(e),
                },
                {
                    type: CommandAlternatePlease.eventType,
                    listener: (el, e) => el.#on_CommandBoxElement_CommandAlternatePlease(e),
                },
                {
                    type: GameCardTagPlease.eventType,
                    selector: '.segment',
                    listener: (el, event) => 
                    // FIXME too messy, open new window instead
                    new TagAGameElement().showTagAGame({
                        game: event.detail.game,
                        container: event.currentTarget,
                    }),
                },
            ],
        }
    }; }
    #on_collectionEditSearchResults_change(a) {
        this.firstSearchResult = a[0];
    }
    async #on_CommandBoxElement_CommandPlease(event) {
        const inputValue = event.detail.input_text_command_box_value;
        if (inputValue) {
            await this.#search(inputValue);
        }
        else {
            // FIXME this.#displayTagless();
        }
    }
    async #on_CommandBoxElement_CommandAlternatePlease(event) {
        const inputValue = event.detail;
        if (inputValue) {
            // FIXME this.#searchHubs(inputValue);
        }
        else {
            // FIXME await this.#displayRecentlyPlayed();
        }
    }
    async #search(query) {
        this.collectionEditSearchResults = await fetchSearchData(query);
    }
}
Customary.declare(CollectionEditAddGamesElement);
//# sourceMappingURL=elements-collection-edit-add-games-steamside.js.map