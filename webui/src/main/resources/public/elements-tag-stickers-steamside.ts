import {Customary, CustomaryElement} from "#customary";
import {CustomaryDeclaration} from "#customary";

import {TagStickerElement} from "#steamside/elements-tag-sticker-steamside.js";

import {Tag} from "#steamside/data-tag.js";

export class TagStickersElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<TagStickersElement> =
		{
			name: 'elements-tag-stickers-steamside',
			config: {
				construct: {shadowRootDont: true},
				attributes: [
					'click_event_on_stickers',
				],
				state: [
					'tags_collection', 'tags_exist',
				],
			},
			values: {
			},
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
		}
	declare tags_exist: boolean;

	#on_changed_tags_collection(a: Tag[]) {
		this.tags_exist = a.length !== 0;
	}
}
Customary.declare(TagStickersElement);
