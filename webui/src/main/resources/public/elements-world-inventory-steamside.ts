import {Customary, CustomaryElement} from "#customary";
import {CustomaryDeclaration} from "#customary";

import {CollectionEditElement} from "#steamside/elements-collection-edit-steamside.js";
import {SteamsideApplication} from "#steamside/application/SteamsideApplication.js";
import {Tag} from "#steamside/data-tag.js";
import {EventBus} from "#steamside/event-bus/EventBus.js";

export class WorldInventoryElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldInventoryElement> =
		{
			name: 'elements-world-inventory-steamside',
			config: {
				state: ['_tag'],
			},
			hooks: {
				requires: [CollectionEditElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					disconnected: el => el.#on_disconnected(),
				},
			}
		}

	declare _tag: Tag;

	async #on_connected()
	{
		const inventory_name = new URLSearchParams(window.location.search)
			.get('name');

		if (!inventory_name) throw new Error('name: missing URL parameter');

		/*
		 https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
        */
		const workaroundFirefox = decodeURIComponent(inventory_name);

		// FIXME collection edit should receive tag name only
		this._tag = {name: workaroundFirefox};

		this.sky.on_connected();

		// MUST be called after eventBus.on_connected() otherwise inner elements can't subscribe
		await this.sky.on_connected_fetchSessionData();
	}

	#on_disconnected()
	{
		this.sky.on_disconnected();
	}

	private readonly sky: SteamsideApplication = new SteamsideApplication(new EventBus(this));
}
Customary.declare(WorldInventoryElement);
