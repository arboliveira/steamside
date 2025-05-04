import { Customary, CustomaryElement } from "#customary";
import { PlayPleaseEvent } from "#steamside/requests/play/PlayPleaseEvent.js";
import { WorldHomeAdvancedModeElement } from "#steamside/elements-world-home-advanced-mode-steamside.js";
import { WorldHomeKidsModeElement } from "#steamside/elements-world-home-kids-mode-steamside.js";
import { PlayRequest } from "#steamside/requests/play/PlayRequest.js";
import { fetchSessionData } from "#steamside/data-session.js";
import { TagPleaseEvent } from "#steamside/requests/tag/TagPleaseEvent.js";
import { TagRequest } from "#steamside/requests/tag/TagRequest.js";
import { UntagPleaseEvent } from "#steamside/requests/untag/UntagPleaseEvent.js";
import { UntagRequest } from "#steamside/requests/untag/UntagRequest.js";
import { GameOverEvent } from "#steamside/requests/play/GameOverEvent.js";
import { NowPlayingEvent } from "#steamside/requests/play/NowPlayingEvent.js";
import { AppTagPleaseEvent } from "#steamside/requests/app/AppTagPleaseEvent.js";
import { AppTagRequest } from "#steamside/requests/app/AppTagRequest.js";
export class WorldHomeElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.dryRun = false;
    }
    static { this.customary = {
        name: 'elements-world-home-steamside',
        config: {
            state: [
                '__advancedMode_visible',
                '__kidsMode_visible', '__kidsMode',
            ],
        },
        hooks: {
            requires: [
                WorldHomeAdvancedModeElement,
                WorldHomeKidsModeElement
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
            },
            changes: {
                '__kidsMode': (el, a) => el.#on_kidsMode_change(a),
            },
            events: [
                {
                    type: PlayPleaseEvent.eventName,
                    listener: (el, event) => el.#on_PlayPleaseEvent(event),
                },
                {
                    type: NowPlayingEvent.eventName,
                    listener: (el, event) => el.#on_NowPlaying(event),
                },
                {
                    type: GameOverEvent.eventName,
                    listener: (el, event) => el.#on_GameOver(event),
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
        },
    }; }
    #on_kidsMode_change(a) {
        if (a) {
            this.__advancedMode_visible = false;
            this.__kidsMode_visible = true;
        }
        else {
            this.__advancedMode_visible = true;
            this.__kidsMode_visible = false;
        }
    }
    async #on_PlayPleaseEvent(event) {
        await PlayRequest.play({ event, dryRun: this.dryRun });
    }
    async #on_TagPleaseEvent(event) {
        await TagRequest.addGameToInventory({ event, dryRun: this.dryRun });
    }
    async #on_UntagPleaseEvent(event) {
        await UntagRequest.removeGameFromInventory({ event, dryRun: this.dryRun });
    }
    #on_NowPlaying(_event) {
        // FIXME dispatch HubRefreshPlease to Continues element so joy is shown
    }
    #on_GameOver(_event) {
        // FIXME dispatch HubRefreshPlease to Continues element so joy time is shown
    }
    async #on_AppTagPleaseEvent(event) {
        await AppTagRequest.tagApp({ event, dryRun: this.dryRun });
    }
    async #on_connected() {
        const sessionData = await fetchSessionData();
        this.__kidsMode = sessionData.kidsMode;
        this.dryRun = sessionData.backoff;
    }
}
Customary.declare(WorldHomeElement);
//# sourceMappingURL=elements-world-home-steamside.js.map