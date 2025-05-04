import { Customary, CustomaryElement } from "#customary";
import { CollectionEditElement } from "#steamside/elements-collection-edit-steamside.js";
import { fetchSessionData } from "#steamside/data-session.js";
import { PlayPleaseEvent } from "#steamside/requests/play/PlayPleaseEvent.js";
import { PlayRequest } from "#steamside/requests/play/PlayRequest.js";
import { TagPleaseEvent } from "#steamside/requests/tag/TagPleaseEvent.js";
import { TagRequest } from "#steamside/requests/tag/TagRequest.js";
import { UntagPleaseEvent } from "#steamside/requests/untag/UntagPleaseEvent.js";
import { UntagRequest } from "#steamside/requests/untag/UntagRequest.js";
import { AppTagPleaseEvent } from "#steamside/requests/app/AppTagPleaseEvent.js";
import { AppTagRequest } from "#steamside/requests/app/AppTagRequest.js";
export class WorldInventoryElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.dryRun = false;
    }
    static { this.customary = {
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
            },
            events: [
                {
                    type: PlayPleaseEvent.eventName,
                    listener: (el, event) => el.#on_PlayPleaseEvent(event),
                },
                {
                    type: TagPleaseEvent.eventName,
                    listener: (el, event) => el.#on_TagPleaseEvent(event),
                },
                {
                    type: UntagPleaseEvent.eventName,
                    listener: (el, event) => el.#on_UntagPleaseEvent(event),
                },
                {
                    type: AppTagPleaseEvent.eventName,
                    listener: (el, event) => el.#on_AppTagPleaseEvent(event),
                },
            ],
        }
    }; }
    async #on_PlayPleaseEvent(event) {
        await PlayRequest.play({ event, dryRun: this.dryRun });
    }
    async #on_TagPleaseEvent(event) {
        await TagRequest.addGameToInventory({ event, dryRun: this.dryRun });
    }
    async #on_UntagPleaseEvent(event) {
        await UntagRequest.removeGameFromInventory({ event, dryRun: this.dryRun });
    }
    async #on_AppTagPleaseEvent(event) {
        await AppTagRequest.tagApp({ event, dryRun: this.dryRun });
    }
    async #on_connected() {
        const inventory_name = new URLSearchParams(window.location.search)
            .get('name');
        if (!inventory_name)
            throw new Error('invalid name');
        /*
         https://github.com/jashkenas/backbone/issues/2566#issuecomment-26065829
        */
        const workaroundFirefox = decodeURIComponent(inventory_name);
        // FIXME collection edit should receive tag name only
        this.__tag = { name: workaroundFirefox };
        const sessionData = await fetchSessionData();
        this.dryRun = sessionData.backoff;
    }
}
Customary.declare(WorldInventoryElement);
//# sourceMappingURL=elements-world-inventory-steamside.js.map