import {Customary, CustomaryElement} from "#customary";
import {CollectionEditElement} from "#steamside/elements-collection-edit-steamside.js";

//import {CustomaryDeclaration} from "#customary";

export class WorldInventoryElement extends CustomaryElement {
	/**
	 * @type {CustomaryDeclaration<WorldInventoryElement>}
	 */
	static customary =
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

	#on_firstUpdated()
	{
	}

	async #on_connected()
	{
		const route = window.location.hash;

		const a = 'collections/';
		const z = '/edit';

		const inventory_name = route.substring(
			route.indexOf(a) + a.length,
			route.lastIndexOf(z)
		);

		/*
		 https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
        */
		const workaroundFirefox = decodeURIComponent(inventory_name);

		// FIXME collection edit should receive tag name only
		this.__tag = {name: workaroundFirefox};
	}
}
Customary.declare(WorldInventoryElement);
