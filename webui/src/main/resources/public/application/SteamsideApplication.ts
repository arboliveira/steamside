import {CombineAndDeleteSourcesPlease} from "#steamside/application/inventory/combine/CombineAndDeleteSourcesPlease.js";
import {CombineAndDeleteSources} from "#steamside/application/inventory/combine/CombineAndDeleteSources.js";
import {AppTagPlease} from "#steamside/application/app/AppTagPlease.js";
import {AppTagRequest} from "#steamside/application/app/AppTagRequest.js";
import {PlayRequest} from "#steamside/application/play/PlayRequest.js";
import {TagRequest} from "#steamside/application/tag/TagRequest.js";
import {UntagPlease} from "#steamside/application/untag/UntagPlease.js";
import {UntagRequest} from "#steamside/application/untag/UntagRequest.js";
import {PlayPlease} from "#steamside/application/play/PlayPleaseEvent.js";
import {TagPlease} from "#steamside/application/tag/TagPlease.js";
import {fetchSessionData, SessionData} from "#steamside/data-session.js";
import {BackendBridge} from "#steamside/application/BackendBridge.js";
import {Subscription} from "#steamside/event-bus/EventBusSubscription.js";
import {EventBus} from "#steamside/event-bus/EventBus.js";

export class SteamsideApplication {
    constructor(
        private readonly eventBus: EventBus,
        readonly options: {
            backend?: BackendBridge
        } = {}
    ) {}

    on_connected() {
        this.eventBus.on_connected();
        this.eventBus.subscribe(...this.subscriptions);
    }

    on_disconnected() {
        this.eventBus.unsubscribe(...this.subscriptions);
        this.eventBus.on_disconnected();
    }

    async on_connected_fetchSessionData(): Promise<SessionData> {
        const sessionData = await fetchSessionData();
        this.dryRun = sessionData.backoff;
        return sessionData;
    }

    private dryRun: boolean = false;

    private readonly subscriptions: Array<Subscription> = [
        {
            type: PlayPlease.eventType,
            listener: event => PlayRequest.on_PlayPleaseEvent(<CustomEvent>event,
                {backend: this.options.backend, dryRun: this.dryRun}
            ),
        },
        {
            type: TagPlease.eventType,
            listener: event => TagRequest.on_TagPleaseEvent(<CustomEvent>event, {dryRun: this.dryRun}),
        },
        {
            type: UntagPlease.eventType,
            listener: event => UntagRequest.on_UntagPleaseEvent(<CustomEvent>event, {dryRun: this.dryRun}),
        },
        {
            type: AppTagPlease.eventType,
            listener: event => AppTagRequest.on_AppTagPleaseEvent(<CustomEvent>event, {dryRun: this.dryRun}),
        },
        {
            type: CombineAndDeleteSourcesPlease.eventType,
            listener: event => CombineAndDeleteSources.on_CombineAndDeleteSourcesPlease(<CustomEvent>event,
                {dryRun: this.dryRun}
            ),
        }
    ];
}
