import {BackendBridge} from "#steamside/application/BackendBridge.js";
import {AppTagDone} from "#steamside/application/app/AppTagDone.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {AppTagPlease} from "#steamside/application/app/AppTagPlease.js";
import {reentry_as_SomethingWentWrong} from "#steamside/application/SomethingWentWrong_reentry.js";
import {AppTagDoing} from "#steamside/application/app/AppTagDoing.js";

export class AppTagRequest {
    static async on_AppTagPleaseEvent(
        event: CustomEvent<AppTagPlease.EventDetail>,
        options: {dryRun: boolean}
    ) {
        const {fun, collection} = event.detail;
        const endpoint = "api/app/" + fun.id + "/tag/" + encodeURIComponent(collection);

        const dryRun = options.dryRun;

        const backend: BackendBridge = new BackendBridge(dryRun ? {dryRun: true} : undefined);

        const switchboard: EventTarget = event.currentTarget!;

        Skyward.orbit<AppTagDoing.EventDetail>(
            switchboard, {
                type: AppTagDoing.eventType,
                detail: {fun, collection, endpoint, dryRun}
            });
        try {
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(endpoint);
        }
        catch (err)
        {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally {
            Skyward.orbit<AppTagDone.EventDetail>(
                switchboard, {type: AppTagDone.eventType, detail: {fun, collection}});
        }
    }
}
