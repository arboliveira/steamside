import { Customary, CustomaryElement } from "#customary";
import { CollectionEditElement } from "#steamside/elements-collection-edit-steamside.js";
export class WorldInventoryElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-world-inventory-steamside',
        config: {
            construct: { shadowRootDont: true },
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
            },
        }
    }; }
    async #on_connected() {
        const inventory_name = new URLSearchParams(window.location.search)
            .get('name');
        if (!inventory_name)
            throw new Error('name: missing URL parameter');
        /*
         https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
        */
        const workaroundFirefox = decodeURIComponent(inventory_name);
        // FIXME collection edit should receive tag name only
        this._tag = { name: workaroundFirefox };
    }
}
Customary.declare(WorldInventoryElement);
//# sourceMappingURL=elements-world-inventory-steamside.js.map