import {BackendBridge} from "#steamside/application/BackendBridge.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {UntagDone} from "#steamside/application/untag/UntagDone.js";
import {UntagDoing} from "#steamside/application/untag/UntagDoing.js";
import {UntagPlease} from "#steamside/application/untag/UntagPlease.js";
import {reentry_as_SomethingWentWrong} from "#steamside/application/SomethingWentWrong_reentry.js";

export class UntagRequest {
    static async on_UntagPleaseEvent(
        event: CustomEvent<UntagPlease.EventDetail>,
        options: {dryRun: boolean}
    ) {
        const {fun, tagName, funName} = event.detail;
        const endpoint = "api/collection/" + tagName + "/remove/" + fun.id;

        const dryRun = options.dryRun;

        const backend: BackendBridge =
            new BackendBridge(dryRun ? {dryRun: true} : undefined);

        Skyward.orbit<UntagDoing.EventDetail>(
            event, {
                type: UntagDoing.eventType,
                detail: {fun, funName, tagName, dryRun, endpoint}
            }
        );
        try
        {
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(endpoint);
        }
        catch (err)
        {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally {
            Skyward.orbit<UntagDone.EventDetail>(
                event, {type: UntagDone.eventType, detail: {fun, tagName}});
        }
    }
}
