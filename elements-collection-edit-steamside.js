import { Customary, CustomaryElement } from "#customary";
import { TagStickerElement } from "#steamside/elements-tag-sticker-steamside.js";
import { GameCardDeckElement } from "#steamside/elements-game-card-deck-steamside.js";
import { pop_toast, toastError, toastOrNot } from "#steamside/vfx-toaster.js";
import { TagAGameElement } from "#steamside/elements-tag-a-game-steamside.js";
import { fetchInventoryContentsOfTag } from "#steamside/data-inventory.js";
import { CollectionEditAddGamesElement } from "#steamside/elements-collection-edit-add-games-steamside.js";
import { CollectionEditCombineElement } from "#steamside/elements-collection-edit-combine-steamside.js";
import { Sideshow } from "#steamside/vfx-sideshow.js";
import { TagPlease } from "#steamside/application/tag/TagPlease.js";
import { HubContentsChangedEvent } from "#steamside/application/hub/HubContentsChangedEvent.js";
import { UntagPlease } from "#steamside/application/untag/UntagPlease.js";
import { GameActionButtonClick } from "#steamside/elements/game-card/GameActionButtonClick.js";
import { GameCardTagPlease } from "#steamside/elements/game-card/GameCardTagPlease.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
import { imagineDryRun } from "#steamside/data-offline-mode.js";
import { Fun } from "#steamside/application/Fun.js";
import { SomethingWentWrong } from "#steamside/application/SomethingWentWrong.js";
import { EventBusSubscribeOnConnected, EventBusUnsubscribeOnDisconnected } from "#steamside/event-bus/EventBusSubscribe.js";
import { UntagDoing } from "#steamside/application/untag/UntagDoing.js";
import { TagDoing } from "#steamside/application/tag/TagDoing.js";
export class CollectionEditElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.subscriptions = [
            {
                type: TagDoing.eventType,
                listener: event => this.#on_TagDoing(event),
            },
            {
                type: UntagDoing.eventType,
                listener: event => this.#on_UntagDoing(event),
            },
        ];
    }
    static { this.customary = {
        name: 'elements-collection-edit-steamside',
        config: {
            define: {
                fontLocations: [
                    "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
                    'https://fonts.googleapis.com/css?family=Karla:regular,bold'
                ],
            },
            attributes: [
                'simplified',
            ],
            state: [
                '_tag', '_inventory',
                'add_games_segment_visible',
                'combine_collection_picker_visible',
            ],
        },
        values: {},
        hooks: {
            requires: [
                TagStickerElement, GameCardDeckElement,
                CollectionEditAddGamesElement,
                CollectionEditCombineElement,
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
                disconnected: el => el.#on_disconnected(),
                firstUpdated: el => el.#on_firstUpdated(),
                willUpdate: el => el.#on_willUpdate(),
            },
            changes: {
                '_tag': (el) => el.#on_changed_tag(),
                '__inventory_dynamic': (el, a) => el._inventory = a,
            },
            events: [
                {
                    type: HubContentsChangedEvent.eventName,
                    listener: (el, e) => el.#on_HubContentsChangedEvent(e),
                },
                {
                    type: GameCardTagPlease.eventType,
                    selector: '.segment',
                    listener: (_el, e) => 
                    // FIXME too messy, open new window instead
                    new TagAGameElement().showTagAGame({
                        game: e.detail.game,
                        container: e.currentTarget,
                    }),
                },
                {
                    type: GameActionButtonClick.eventType,
                    listener: (el, e) => el.#on_GameActionButtonClick(e),
                },
                {
                    type: 'click',
                    selector: "#side-link-combine",
                    listener: (el, e) => el.#combineClicked(e),
                },
                {
                    type: SomethingWentWrong.eventType,
                    listener: (el, event) => el.#on_SomethingWentWrong(event),
                },
            ],
        }
    }; }
    #on_willUpdate() {
        const read_only = this._tag?.builtin
            || (this.simplified === 'true');
        this.add_games_segment_visible = !read_only;
    }
    #on_GameActionButtonClick(event) {
        switch (event.detail.action_button) {
            case 'add':
                this.#on_GameActionAdd(event);
                break;
            case 'remove':
                this.#on_GameActionRemove(event);
                break;
        }
    }
    #on_GameActionAdd(event) {
        Skyward.fly(this, {
            type: TagPlease.eventType,
            detail: {
                fun: new Fun(event.detail.game.appid),
                funName: event.detail.game.name,
                tagName: this._tag.name
            }
        });
    }
    #on_GameActionRemove(event) {
        Skyward.fly(this, {
            type: UntagPlease.eventType,
            detail: {
                tagName: this._tag.name,
                fun: new Fun(event.detail.game.appid),
                funName: event.detail.game.name
            }
        });
    }
    #on_TagDoing(e) {
        if (e.detail.tagName !== this._tag.name)
            return;
        const { tagName, funName, dryRun, endpoint } = e.detail;
        toastOrNot({
            content: imagineDryRun({ imagine: `${funName} was added to ${tagName}`, url: endpoint, dryRun }),
            target: this.renderRoot.lastElementChild
        });
    }
    #on_UntagDoing(e) {
        if (e.detail.tagName !== this._tag.name)
            return;
        const { tagName, funName, dryRun, endpoint } = e.detail;
        toastOrNot({
            content: imagineDryRun({
                imagine: `${funName} was removed from ${tagName}`, url: endpoint, dryRun
            }),
            target: this.renderRoot.lastElementChild
        });
    }
    #on_SomethingWentWrong(event) {
        ///////if (event.detail.originatingTarget !== this) return;
        ///////event.stopPropagation();
        toastError({ content: event.detail.error, target: this.renderRoot.lastElementChild });
    }
    async #on_HubContentsChangedEvent(e) {
        if (this._tag?.name === e.detail.tagName) {
            await this.#fetch_inventory();
        }
        // FIXME nice to have: animate the card dropping into the inventory
        // FIXME nice to have: filter tagged cards out of search results
    }
    async #fetch_inventory() {
        if (!this._tag)
            return;
        this._inventory = await fetchInventoryContentsOfTag(this._tag);
    }
    async #on_changed_tag() {
        try {
            await this.#fetch_inventory();
        }
        catch (error) {
            pop_toast({ error: error, target: this });
        }
    }
    #combineClicked(e) {
        e.preventDefault();
        this.combine_collection_picker_visible = true;
    }
    #on_firstUpdated() {
        Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
    }
    #on_connected() {
        EventBusSubscribeOnConnected.subscribe(this, this.subscriptions);
    }
    #on_disconnected() {
        EventBusUnsubscribeOnDisconnected.unsubscribe(this, this.subscriptions);
    }
}
Customary.declare(CollectionEditElement);
//# sourceMappingURL=elements-collection-edit-steamside.js.map