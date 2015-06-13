import {Customary, CustomaryElement} from "#customary";
import {CollectionEditElement} from "#steamside/elements-collection-edit-steamside.js";

import {CustomaryDeclaration} from "#customary";
import {Tag} from "#steamside/data-tag";

export class WorldInventoryElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldInventoryElement> =
		{
			name: 'elements-world-inventory-steamside',
			config: {
				state: ['__tag'],
			},
			hooks: {
				requires: [CollectionEditElement],
				externalLoader: {
					import_meta: import.meta,
					css_dont: true,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
					firstUpdated: el => el.#on_firstUpdated(),
				},
			}
		}
	declare __tag: Tag;

	#on_firstUpdated()
	{
	}

	async #on_connected()
	{
		const inventory_name = new URLSearchParams(window.location.search)
			.get('name');

		if (!inventory_name) throw new Error('invalid name');

		/*
		 https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
        */
		const workaroundFirefox = decodeURIComponent(inventory_name);

		// FIXME collection edit should receive tag name only
		this.__tag = {name: workaroundFirefox};
	}
}
Customary.declare(WorldInventoryElement);
