import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {fetchTagsData} from "#steamside/data-tags.js";
import {TagStickersElement} from "#steamside/elements-tag-stickers-steamside.js";
import {Sideshow} from "#steamside/vfx-sideshow.js";

import {Tag} from "#steamside/data-tag.js";
import {
	TagStickerElement_TagClicked_eventDetail,
	TagStickerElement_TagClicked_eventName
} from "#steamside/elements/tag-sticker/TagStickerElement_TagClicked_Event.js";
import {CollectionPicked} from "#steamside/elements/collection-picker/CollectionPicked.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";

export class CollectionPickerElement extends CustomaryElement {
	static customary: CustomaryDeclaration<CollectionPickerElement> =
		{
			name: 'elements-collection-picker-steamside',
			config: {
				attributes: [
					'click_event_on_stickers'
				],
				state: [
					'tags',
				],
			},
			values: {
			},
			hooks: {
 				requires: [TagStickersElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					firstUpdated: el => el.#on_firstUpdated(),
				},
				changes: {
				},
				events: [
					{
						type: TagStickerElement_TagClicked_eventName,
						listener: (el, e) => el.#on_TagStickerElement_TagClicked(<CustomEvent>e),
					},
				],
			}
		}
	declare tags: Tag[];

	#on_TagStickerElement_TagClicked(event: CustomEvent<TagStickerElement_TagClicked_eventDetail>) {
		Skyward.stage<CollectionPicked.EventDetail>(
			event, this, {type: CollectionPicked.eventType, detail: {tagName: event.detail.tagName}});
	}

	#on_firstUpdated() {
		Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
	}

	async #on_connected()
	{
		this.tags = await fetchTagsData();
	}

}
Customary.declare(CollectionPickerElement);
