import { Customary, CustomaryElement } from "#customary";
import { TagStickerElement } from "#steamside/elements-tag-sticker-steamside.js";
import { GameCardDeckElement } from "#steamside/elements-game-card-deck-steamside.js";
import { pop_toast, toastOrNot } from "#steamside/vfx-toaster.js";
import { TagAGameElement } from "#steamside/elements-tag-a-game-steamside.js";
import { fetchInventoryContentsOfTag } from "#steamside/data-inventory.js";
import { CollectionEditAddGamesElement } from "#steamside/elements-collection-edit-add-games-steamside.js";
import { CollectionEditCombineElement } from "#steamside/elements-collection-edit-combine-steamside.js";
import { Sideshow } from "#steamside/vfx-sideshow.js";
import { TagPleaseEvent } from "#steamside/requests/tag/TagPleaseEvent.js";
import { HubContentsChangedEvent } from "#steamside/engines/hub/HubContentsChangedEvent.js";
import { UntagPleaseEvent } from "#steamside/requests/untag/UntagPleaseEvent.js";
import { UntagDoneEvent } from "#steamside/requests/untag/UntagDoneEvent.js";
import { GameCardElement_ActionButtonClick_eventName } from "#steamside/elements/game-card/GameCardElement_ActionButtonClick_Event.js";
export class CollectionEditElement extends CustomaryElement {
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
                    type: UntagDoneEvent.eventName,
                    listener: (el, e) => el.#on_UntagDoneEvent(e),
                },
                {
                    type: GameCardElement_ActionButtonClick_eventName,
                    selector: '.segment',
                    listener: (el, e) => el.#on_GameCardElement_ActionButtonClick(e),
                },
                {
                    type: 'click',
                    selector: "#side-link-combine",
                    listener: (el, e) => el.#combineClicked(e),
                },
            ],
        }
    }; }
    #on_willUpdate() {
        const read_only = this._tag?.builtin
            || (this.simplified === 'true');
        this.add_games_segment_visible = !read_only;
    }
    async #on_GameCardElement_ActionButtonClick(event) {
        switch (event.detail.action_button) {
            case 'add':
                this.dispatchEvent(new TagPleaseEvent({
                    tagName: this._tag.name,
                    game: event.detail.game,
                    originator: event.detail.originator,
                }));
                break;
            case 'remove':
                this.dispatchEvent(new UntagPleaseEvent({
                    tagName: this._tag.name,
                    game: event.detail.game,
                    originator: event.detail.originator,
                }));
                break;
            case 'tag':
                // FIXME too messy, open new window instead
                new TagAGameElement().showTagAGame({
                    game: event.detail.game,
                    container: event.currentTarget
                });
                break;
        }
    }
    async #on_HubContentsChangedEvent(e) {
        if (this._tag?.name === e.detail.tagName) {
            await this.#fetch_inventory();
        }
        // FIXME nice to have: animate the card dropping into the inventory
        // FIXME nice to have: filter tagged cards out of search results
    }
    async #on_UntagDoneEvent(untagDoneEvent) {
        toastOrNot({
            content: untagDoneEvent.detail.toast_content,
            target: this.renderRoot.lastElementChild,
        });
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
}
Customary.declare(CollectionEditElement);
//# sourceMappingURL=elements-collection-edit-steamside.js.map