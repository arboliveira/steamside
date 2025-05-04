import {Customary, CustomaryElement} from "#customary";
import {CustomaryDeclaration} from "#customary";

import {Tag} from "#steamside/data-tag.js";
import {
	TagStickerElement_TagClicked_eventDetail,
	TagStickerElement_TagClicked_eventName
} from "#steamside/elements/tag-sticker/TagStickerElement_TagClicked_Event.js";

export class TagStickerElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<TagStickerElement> =
		{
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
				lifecycle: {
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
		}

	declare click_event: string;
	declare tag: Tag;
	declare url: string;
	declare count_visible: boolean;

	#on_click(e: Event) {
		if (this.click_event !== 'true') {
			e.preventDefault();
			this.dispatchEvent(
				new CustomEvent<TagStickerElement_TagClicked_eventDetail>(
					TagStickerElement_TagClicked_eventName,
					{
						detail: {tagName: this.tag.name},
						composed: true,
						bubbles: true,
					}
				)
			);
		}
	}

	#on_changed_tag(a: Tag) {
		this.count_visible = a.count !== undefined;
		
		const surface = this.renderRoot.querySelector('a')!;

		if (this.tag.builtin)
		{
			surface.classList.remove('sticker-curated');
			surface.classList.add('sticker-builtin');
		}
		else
		{
			surface.classList.add('sticker-curated');
			surface.classList.remove('sticker-builtin');
			
			const name_text = this.tag.name;
			const encoded = encodeURIComponent(name_text);
			this.url = `./InventoryWorld.html?name=${encoded}`;
		}
	}

}
Customary.declare(TagStickerElement);
