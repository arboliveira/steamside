import { Customary, CustomaryElement } from "#customary";
import { Backend } from "#steamside/data-backend.js";
import { CollectionPickerElement } from "#steamside/elements-collection-picker-steamside.js";
import { TagStickerElement_TagClicked_eventName } from "#steamside/elements/tag-sticker/TagStickerElement_TagClicked_Event.js";
import { CollectionPicked } from "#steamside/elements/collection-picker/CollectionPicked.js";
export class WorldSettingsKidsKidEditElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.backend = new Backend();
    }
    static { this.customary = {
        name: 'elements-world-settings-kids-kid-edit-steamside',
        config: {
            attributes: [
                'kid_id',
                'kid_name',
                'kid_user',
                'kid_inventory',
            ],
            state: [
                '__kid',
                '__collection_picker_visible',
                '__can_see_and_play_sticker_tag'
            ],
            define: {
                fontLocations: [
                    "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
                    'https://fonts.googleapis.com/css?family=Jura:regular',
                    'https://fonts.googleapis.com/css?family=Karla:regular',
                ],
            },
            construct: { shadowRootDont: true },
        },
        values: {},
        hooks: {
            requires: [CollectionPickerElement],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
                willUpdate: el => el.#on_willUpdate(),
                firstUpdated: el => el.#on_firstUpdated(),
            },
            changes: {
                '__kid': (el, a) => el.#on_kid_change(a),
            },
            events: [
                {
                    type: TagStickerElement_TagClicked_eventName,
                    listener: (el) => el.#on_TagStickerElement_TagClicked(),
                },
                {
                    type: CollectionPicked.eventType,
                    listener: (el, e) => el.#on_CollectionPickerElement_CollectionPicked(e),
                },
                {
                    selector: "#SaveButton",
                    listener: (el) => el.#on_Save_click(),
                },
                {
                    selector: "#DeleteKid",
                    listener: (el, e) => el.#on_DeleteKid_click(e),
                },
                {
                    type: "click",
                    selector: "#side-link-inventory-switch",
                    listener: (el, e) => el.#on_inventory_switch_click(e),
                }
            ],
        }
    }; }
    #on_kid_change(a) {
        this.kid_id = a.id;
        this.kid_name = a.name;
        this.kid_user = a.user;
        this.kid_inventory = a.inventory_tag_for_sticker?.name;
    }
    #on_Save_click() {
        const kid = this.#thisKid();
        this.dispatchEvent(new CustomEvent('WorldSettingsKidsKidEditElement:SavePlease', {
            detail: { data: { kid } },
            composed: true,
            bubbles: true,
        }));
        // FIXME parent should call remove if backend succeeds, leave it showing if fails 
        this.remove();
    }
    #thisKid() {
        return {
            id: this.kid_id,
            name: this.kid_name,
            user: this.kid_user,
            inventory: this.kid_inventory,
        };
    }
    #on_DeleteKid_click(e) {
        e.preventDefault();
        const kid = this.#thisKid();
        this.dispatchEvent(new CustomEvent('WorldSettingsKidsKidEditElement:DeletePlease', {
            detail: { data: { kid } },
            composed: true,
            bubbles: true,
        }));
        // FIXME parent should call remove if backend succeeds, leave it showing if fails
        this.remove();
    }
    #on_inventory_switch_click(e) {
        e.preventDefault();
        this.#switchInventory();
    }
    #on_willUpdate() {
        this.__can_see_and_play_sticker_tag =
            this.kid_inventory
                ? { name: this.kid_inventory }
                : { name: 'Choose...' };
    }
    #on_TagStickerElement_TagClicked() {
        this.#switchInventory();
    }
    #on_CollectionPickerElement_CollectionPicked(e) {
        // FIXME receive or look up real tag so we can display count
        this.kid_inventory = e.detail.tagName;
        this.__collection_picker_visible = false;
        this.requestUpdate();
    }
    #switchInventory() {
        this.__collection_picker_visible = true;
    }
    #on_firstUpdated() {
        const input = this.renderRoot.querySelector("input[data-customary-bind='kid_name']");
        input.focus();
    }
    async #on_connected() {
        await this.backend.fetchSessionDataAndDisableBackendIfOffline();
    }
}
Customary.declare(WorldSettingsKidsKidEditElement);
//# sourceMappingURL=elements-world-settings-kids-kid-edit-steamside.js.map