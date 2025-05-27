import { Customary, CustomaryElement } from "#customary";
import { GameCardElement } from "#steamside/elements-game-card-steamside.js";
import { TagStickersElement } from "#steamside/elements-tag-stickers-steamside.js";
import { CommandBoxElement } from "#steamside/elements-command-box-steamside.js";
import { fetchTagSuggestionsData } from "#steamside/data-tag-suggestions.js";
import { toastError, toastOrNot } from "#steamside/vfx-toaster.js";
import { TagStickerElement_TagClicked_eventName } from "#steamside/elements/tag-sticker/TagStickerElement_TagClicked_Event.js";
import { AppTagDone } from "#steamside/application/app/AppTagDone.js";
import { AppTagPlease } from "#steamside/application/app/AppTagPlease.js";
import { CommandPlease } from "#steamside/elements/command-box/CommandPlease.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
import { Fun } from "#steamside/application/Fun.js";
import { imagineDryRun } from "#steamside/data-offline-mode.js";
import { EventBusSubscribeOnConnected, EventBusUnsubscribeOnDisconnected } from "#steamside/event-bus/EventBusSubscribe.js";
import { AppTagDoing } from "#steamside/application/app/AppTagDoing.js";
import { SomethingWentWrong } from "#steamside/application/SomethingWentWrong.js";
export class TagAGameElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.subscriptions = [
            {
                type: AppTagDoing.eventType,
                listener: event => this.#on_AppTagDoing(event),
            },
        ];
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
                connected: el => el.#on_connected(),
                disconnected: el => el.#on_disconnected(),
                willUpdate: el => el.#on_willUpdate(),
            },
            changes: {},
            events: [
                {
                    type: 'CommandBoxElement:InputValueChanged',
                    listener: (el, event) => el.#on_changed_input_text_command_box_value(event),
                },
                {
                    type: CommandPlease.eventType,
                    listener: (el, event) => el.#on_CommandBoxElement_CommandPlease(event),
                },
                {
                    type: TagStickerElement_TagClicked_eventName,
                    listener: (el, e) => el.#on_TagStickerElement_TagClicked(e),
                },
                {
                    type: AppTagDone.eventType,
                    listener: (el, e) => el.#on_AppTagDoneEvent(e),
                },
                {
                    type: SomethingWentWrong.eventType,
                    listener: (el, event) => el.#on_SomethingWentWrong(event),
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
    async #on_CommandBoxElement_CommandPlease(event) {
        const nameForCollection = this.#nameForCollection(event.detail.input_text_command_box_value);
        await this.#addTagToGame(nameForCollection, event);
    }
    async #addTagToGame(collection, event) {
        Skyward.stage(event, this, {
            type: AppTagPlease.eventType,
            detail: {
                fun: new Fun(this.game.appid), collection
            }
        });
    }
    #on_AppTagDoing(e) {
        if (e.detail.fun.id !== this.game.appid)
            return;
        const funName = this.game.name;
        const { collection, dryRun, endpoint } = e.detail;
        toastOrNot({
            content: imagineDryRun({ imagine: `${funName} was added to ${collection}`, url: endpoint, dryRun }),
            target: this.renderRoot.lastElementChild
        });
    }
    async #on_AppTagDoneEvent(_appTagDoneEvent) {
        const commandBox = this.renderRoot.querySelector('elements-command-box-steamside');
        commandBox.set_input_text_command_box_value('');
    }
    #on_SomethingWentWrong(event) {
        /////// if (event.detail.originatingTarget !== this) return;
        /////// event.stopPropagation();
        toastError({ content: event.detail.error, target: this.renderRoot.lastElementChild });
    }
    async #on_TagStickerElement_TagClicked(event) {
        event.stopPropagation();
        await this.#addTagToGame(event.detail.tagName, event);
    }
    #on_willUpdate() {
        this.commandHintsSubject = this.#nameForCollection(this.command_box_entered);
    }
    #nameForCollection(input) {
        return input?.trim() || "Favorites";
    }
    async #on_connected() {
        EventBusSubscribeOnConnected.subscribe(this, this.subscriptions);
        this.suggestions = await fetchTagSuggestionsData();
    }
    #on_disconnected() {
        EventBusUnsubscribeOnDisconnected.unsubscribe(this, this.subscriptions);
    }
}
Customary.declare(TagAGameElement);
//# sourceMappingURL=elements-tag-a-game-steamside.js.map