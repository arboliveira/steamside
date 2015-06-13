import { Customary, CustomaryElement } from "#customary";
import { TagStickerElement } from "#steamside/elements-tag-sticker-steamside.js";
export class TagStickersElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-tag-stickers-steamside',
        config: {
            attributes: [
                'click_event_on_stickers',
            ],
            state: [
                'tags_collection', 'tags_exist',
            ],
        },
        values: {},
        hooks: {
            requires: [TagStickerElement],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            changes: {
                'tags_collection': (el, a) => el.#on_changed_tags_collection(a),
            },
        }
    }; }
    #on_changed_tags_collection(a) {
        this.tags_exist = a.length !== 0;
    }
}
Customary.declare(TagStickersElement);
//# sourceMappingURL=elements-tag-stickers-steamside.js.map