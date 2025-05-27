import { BackendBridge } from "#steamside/application/BackendBridge.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
import { TagDone } from "#steamside/application/tag/TagDone.js";
import { TagDoing } from "#steamside/application/tag/TagDoing.js";
import { reentry_as_SomethingWentWrong } from "#steamside/application/SomethingWentWrong_reentry.js";
export class TagRequest {
    static async on_TagPleaseEvent(event, options) {
        const { fun, funName, tagName } = event.detail;
        const endpoint = "api/collection/" + tagName + "/add/" + fun.id;
        const dryRun = options.dryRun;
        const backend = new BackendBridge(dryRun ? { dryRun: true } : undefined);
        Skyward.orbit(event, { type: TagDoing.eventType, detail: { fun, funName, tagName, endpoint, dryRun } });
        try {
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(endpoint);
        }
        catch (err) {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally {
            Skyward.orbit(event, { type: TagDone.eventType, detail: { fun, tagName } });
        }
    }
}
//# sourceMappingURL=TagRequest.js.map