import { Customary, CustomaryElement } from "#customary";
import { Backend } from "#steamside/data-backend.js";
import { GameCardElement } from "#steamside/elements-game-card-steamside.js";
import { TagStickersElement } from "#steamside/elements-tag-stickers-steamside.js";
import { CommandBoxElement } from "#steamside/elements-command-box-steamside.js";
import { fetchTagSuggestionsData } from "#steamside/data-tag-suggestions.js";
import { pop_toast } from "#steamside/vfx-toaster.js";
export class TagAGameElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.backend = new Backend();
    }
    static { this.customary = {
        name: 'elements-tag-a-game-steamside',
        config: {
            define: {
                fontLocation: "https://fonts.googleapis.com/css?family=Arvo:regular",
            },
            attributes: [],
            state: [
                'game',
                'command_box_entered', 'commandHintsSubject',
                'suggestions',
            ],
        },
        values: {},
        hooks: {
            requires: [
                GameCardElement,
                TagStickersElement,
                CommandBoxElement
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                willUpdate: el => el.#on_willUpdate(),
                connected: el => el.#on_connected(),
            },
            changes: {},
            events: [
                {
                    type: 'CommandBoxElement:InputValueChanged',
                    listener: (el, event) => el.#on_changed_input_text_command_box_value(event),
                },
                {
                    type: 'CommandBoxElement:CommandPlease',
                    listener: (el, event) => el.#on_CommandBoxElement_CommandPlease(event.detail, event),
                },
            ],
        }
    }; }
    showTagAGame({ game, container }) {
        this.game = game;
        container.appendChild(this);
    }
    #on_changed_input_text_command_box_value(event) {
        this.command_box_entered = event.detail;
    }
    async #on_CommandBoxElement_CommandPlease(detail, event) {
        const nameForCollection = this.#nameForCollection(detail);
        await this.#addTagToGame(nameForCollection, event.target);
    }
    async #addTagToGame(collection, ui) {
        const appid = this.game.appid;
        const aUrl = "api/app/" + appid + "/tag/" + encodeURIComponent(collection);
        try {
            // FIXME display 'tagging...'
            await this.backend.fetchBackend({ url: aUrl });
        }
        catch (error) {
            // FIXME display 'tagging failed'
            pop_toast({
                error: error,
                offline_imagine_spot: `${this.game.name} was added to ${collection}`,
                target: ui,
                completion_fn: () => this.#on_tagging_complete(),
            });
        }
    }
    #on_tagging_complete() {
        const commandBox = this.renderRoot.querySelector('elements-command-box-steamside');
        commandBox.set_input_text_command_box_value('');
    }
    #on_willUpdate() {
        this.commandHintsSubject = this.#nameForCollection(this.command_box_entered);
    }
    #nameForCollection(input) {
        return input?.trim() || "Favorites";
    }
    async #on_connected() {
        await this.backend.fetchSessionDataAndDisableBackendIfOffline();
        this.suggestions = await fetchTagSuggestionsData();
    }
}
Customary.declare(TagAGameElement);
//# sourceMappingURL=elements-tag-a-game-steamside.js.map