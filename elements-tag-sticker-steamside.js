import { Customary, CustomaryElement } from "#customary";
import { TagStickerElement_TagClicked_eventName } from "#steamside/elements/tag-sticker/TagStickerElement_TagClicked_Event.js";
export class TagStickerElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-tag-sticker-steamside',
        config: {
            construct: { shadowRootDont: true },
            attributes: [
                'click_event'
            ],
            state: [
                'tag',
                'count_visible',
                'url',
            ],
        },
        values: {
            'count_visible': true,
        },
        hooks: {
            externalLoader: {
                import_meta: import.meta,
            },
            changes: {
                'tag': (el, a) => el.#on_changed_tag(a),
            },
            events: [{
                    type: 'click',
                    selector: '.tag-outer-div',
                    listener: (el, e) => el.#on_click(e),
                }],
        }
    }; }
    #on_click(e) {
        if (this.click_event !== 'true') {
            e.preventDefault();
            this.dispatchEvent(new CustomEvent(TagStickerElement_TagClicked_eventName, {
                detail: { tagName: this.tag.name },
                composed: true,
                bubbles: true,
            }));
        }
    }
    #on_changed_tag(a) {
        this.count_visible = a.count !== undefined;
        const surface = this.renderRoot.querySelector('.inventory-name-sticker');
        if (this.tag.builtin) {
            surface.classList.remove('sticker-curated');
            surface.classList.add('sticker-builtin');
        }
        else {
            surface.classList.add('sticker-curated');
            surface.classList.remove('sticker-builtin');
            const name_text = this.tag.name;
            const encoded = encodeURIComponent(name_text);
            this.url = `./InventoryWorld.html?name=${encoded}`;
        }
    }
}
Customary.declare(TagStickerElement);
//# sourceMappingURL=elements-tag-sticker-steamside.js.map