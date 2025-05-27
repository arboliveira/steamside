import { CombineAndDeleteSourcesPlease } from "#steamside/application/inventory/combine/CombineAndDeleteSourcesPlease.js";
import { CombineAndDeleteSources } from "#steamside/application/inventory/combine/CombineAndDeleteSources.js";
import { AppTagPlease } from "#steamside/application/app/AppTagPlease.js";
import { AppTagRequest } from "#steamside/application/app/AppTagRequest.js";
import { PlayRequest } from "#steamside/application/play/PlayRequest.js";
import { TagRequest } from "#steamside/application/tag/TagRequest.js";
import { UntagPlease } from "#steamside/application/untag/UntagPlease.js";
import { UntagRequest } from "#steamside/application/untag/UntagRequest.js";
import { PlayPlease } from "#steamside/application/play/PlayPleaseEvent.js";
import { TagPlease } from "#steamside/application/tag/TagPlease.js";
import { fetchSessionData } from "#steamside/data-session.js";
export class SteamsideApplication {
    constructor(eventBus, options = {}) {
        this.eventBus = eventBus;
        this.options = options;
        this.dryRun = false;
        this.subscriptions = [
            {
                type: PlayPlease.eventType,
                listener: event => PlayRequest.on_PlayPleaseEvent(event, { backend: this.options.backend, dryRun: this.dryRun }),
            },
            {
                type: TagPlease.eventType,
                listener: event => TagRequest.on_TagPleaseEvent(event, { dryRun: this.dryRun }),
            },
            {
                type: UntagPlease.eventType,
                listener: event => UntagRequest.on_UntagPleaseEvent(event, { dryRun: this.dryRun }),
            },
            {
                type: AppTagPlease.eventType,
                listener: event => AppTagRequest.on_AppTagPleaseEvent(event, { dryRun: this.dryRun }),
            },
            {
                type: CombineAndDeleteSourcesPlease.eventType,
                listener: event => CombineAndDeleteSources.on_CombineAndDeleteSourcesPlease(event, { dryRun: this.dryRun }),
            }
        ];
    }
    on_connected() {
        this.eventBus.on_connected();
        this.eventBus.subscribe(...this.subscriptions);
    }
    on_disconnected() {
        this.eventBus.unsubscribe(...this.subscriptions);
        this.eventBus.on_disconnected();
    }
    async on_connected_fetchSessionData() {
        const sessionData = await fetchSessionData();
        this.dryRun = sessionData.backoff;
        return sessionData;
    }
}
//# sourceMappingURL=SteamsideApplication.js.map