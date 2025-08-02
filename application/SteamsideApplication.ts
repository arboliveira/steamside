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
import {BackendBridge} from "#steamside/application/BackendBridge.js";
import {Subscription} from "#steamside/event-bus/EventBusSubscription.js";
import {EventBus} from "#steamside/event-bus/EventBus.js";
import {WriteOperations} from "#steamside/application/localfirst/WriteOperations.js";
import {StorageBridge} from "#steamside/application/localfirst/StorageBridge.js";
import {WallpaperSettingsRead} from "#steamside/application/modules/settings/WallpaperSettingsRead.js";
import {WallpaperSettingsWrite} from "#steamside/application/modules/settings/WallpaperSettingsWrite.js";
import {ReadOperations} from "#steamside/application/localfirst/ReadOperations.js";
import {SessionHandshake} from "#steamside/application/session/SessionHandshake.js";
import {KidsModeRead} from "#steamside/application/modules/kids/KidsModeRead.js";
import {AllDataRead} from "#steamside/application/modules/backup/AllDataRead.js";
import {AllDataWrite} from "#steamside/application/modules/backup/AllDataWrite.js";

export class SteamsideApplication {
    constructor(
        readonly options: {
            backend?: BackendBridge,
            eventBus: {
                switchboard: EventTarget,
            }
        }
    ) {}
    private readonly sessionHandshake = new SessionHandshake();
    private readonly eventBus = new EventBus({switchboard: this.options.eventBus.switchboard});
    private readonly readOperations =
        new ReadOperations(new StorageBridge(localStorage));
    private readonly writeOperations =
        new WriteOperations(new StorageBridge(localStorage));

    async on_connected() {
        this.sessionHandshake.on_connected();
        this.eventBus.on_connected();
        this.eventBus.subscribe(...this.subscriptions);

        const sessionData = await this.sessionHandshake.getSessionData();
        this.dryRun = sessionData.backoff;
    }

    on_disconnected() {
        this.eventBus.unsubscribe(...this.subscriptions);
        this.eventBus.on_disconnected();
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
        },
        {
            type: AllDataRead.eventTypePlease,
            listener: event => new AllDataRead.Command(this.readOperations).execute(<CustomEvent>event),
        },
        {
            type: AllDataWrite.eventTypePlease,
            listener: event => new AllDataWrite.Command(this.writeOperations).execute(<CustomEvent>event),
        },
        {
            type: KidsModeRead.eventTypePlease,
            listener: event => new KidsModeRead.Command(this.sessionHandshake).execute(event),
        },
        {
            type: WallpaperSettingsRead.eventTypePlease,
            listener: event => new WallpaperSettingsRead.Command(this.readOperations).execute(event),
        },
        {
            type: WallpaperSettingsWrite.eventTypePlease,
            listener: event => new WallpaperSettingsWrite.Command(this.writeOperations)
                .execute(<CustomEvent>event, {dryRun: this.dryRun}),
        },
    ];
}
