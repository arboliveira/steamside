import {BackendBridge} from "#steamside/application/BackendBridge.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {TagDone} from "#steamside/application/tag/TagDone.js";
import {TagDoing} from "#steamside/application/tag/TagDoing.js";
import {TagPlease} from "#steamside/application/tag/TagPlease.js";
import {reentry_as_SomethingWentWrong} from "#steamside/application/SomethingWentWrong_reentry.js";

export class TagRequest {
    static async on_TagPleaseEvent(
        event: CustomEvent<TagPlease.EventDetail>,
        options: {dryRun: boolean}
    )
    {
        const {fun, funName, tagName} = event.detail;
        const endpoint = "api/collection/" + tagName + "/add/" + fun.id;

        const dryRun = options.dryRun;

        const backend: BackendBridge =
            new BackendBridge(dryRun ? {dryRun: true} : undefined);

        const switchboard: EventTarget = event.currentTarget!;

        Skyward.orbit<TagDoing.EventDetail>(
            switchboard, {type: TagDoing.eventType, detail: {fun, funName, tagName, endpoint, dryRun}});
        try
        {
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(endpoint);
        }
        catch (err)
        {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally
        {
            Skyward.orbit<TagDone.EventDetail>(
                switchboard, {type: TagDone.eventType, detail: {fun, tagName}});
        }
    }
}
