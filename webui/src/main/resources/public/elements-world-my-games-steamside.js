import {Customary, CustomaryElement} from "#customary";
import {Sideshow} from "#steamside/vfx-sideshow.js";
import {CollectionPickerElement} from "#steamside/elements-collection-picker-steamside.js";
import {fetchOwnedCountData} from "#steamside/data-owned-count-tag.js";
import {fetchTaglessCountData} from "#steamside/data-tagless-count-tag.js";
import {CollectionEditElement} from "#steamside/elements-collection-edit-steamside.js";

//import {CustomaryDeclaration} from "#customary";

export class WorldMyGamesElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<WorldMyGamesElement>}
	 */
	static customary =
		{
			name: 'elements-world-my-games-steamside',
			config: {
				state: [
					'__tagless_tag', 
					'__owned_tag',
				]
			},
			hooks: {
				requires: [CollectionPickerElement, CollectionEditElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					firstUpdated: el => el.#on_firstUpdated(),
				},
				events: [{
					type: 'CustomaryEvent:lifecycle:firstUpdated',
					listener: (el, e) => el.#on_CustomaryEvent_lifecycle_firstUpdated(e),
				}]
			}
		}

	#on_CustomaryEvent_lifecycle_firstUpdated(event) {
		this.#sideshow.on_firstUpdated_Event(event);
	}

	#on_firstUpdated()
	{
		this.#sideshow.showtime(this);
		Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
	}

	async #on_connected()
	{
		this.__tagless_tag = await fetchTaglessCountData();
		this.__owned_tag = await fetchOwnedCountData();
	}

	#sideshow = new Sideshow();
}
Customary.declare(WorldMyGamesElement);
