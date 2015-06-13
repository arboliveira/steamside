import { Customary, CustomaryElement } from "#customary";
export class TagStickerElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-tag-sticker-steamside',
        config: {
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
            requires: [],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {},
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
            const name = this.tag.name;
            this.dispatchEvent(new CustomEvent('TagStickerElement:TagClicked', {
                detail: name,
                composed: true,
                bubbles: true,
            }));
        }
    }
    #on_changed_tag(a) {
        this.count_visible = a.count !== undefined;
        const surface = this.renderRoot.querySelector('a');
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