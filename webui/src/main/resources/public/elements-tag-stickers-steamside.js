import {Customary, CustomaryElement} from "#customary";
import {TagStickerElement} from "#steamside/elements-tag-sticker-steamside.js";

//import {CustomaryDeclaration} from "#customary";

export class TagStickersElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<TagStickersElement>}
	 */
	static customary =
		{
			name: 'elements-tag-stickers-steamside',
			config: {
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
				lifecycle: {
					connected: el => el.#on_connected(),
				},
				changes: {
					'tags_collection': (el, a) => el.#on_changed_tags_collection(a),
				},
				events: {
				},
			}
		}

	#on_changed_tags_collection(a) {
		this.tags_exist = a.length !== 0;
	}

	async #on_connected()
	{
	}

}
Customary.declare(TagStickersElement);
