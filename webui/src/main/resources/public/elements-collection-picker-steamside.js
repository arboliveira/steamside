import {Customary, CustomaryElement} from "#customary";
import {fetchTagsData} from "#steamside/data-tags.js";
import {TagStickersElement} from "#steamside/elements-tag-stickers-steamside.js";
import {Sideshow} from "#steamside/vfx-sideshow.js";

//import {CustomaryDeclaration} from "#customary";

export class CollectionPickerElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<CollectionPickerElement>}
	 */
	static customary =
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
						type: 'TagStickerElement:TagClicked',
						listener: (el, e) => el.#on_click_one_tag(e),
					},
				],
			}
		}

	#on_click_one_tag(event) {
		event.stopPropagation();
		const name = event.detail;
		this.dispatchEvent(
			new CustomEvent(
				'CollectionPickerElement:CollectionPicked',
				{
					detail: name,
					composed: true,
				}
			)
		);
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
