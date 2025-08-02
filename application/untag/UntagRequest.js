import { BackendBridge } from "#steamside/application/BackendBridge.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
import { UntagDone } from "#steamside/application/untag/UntagDone.js";
import { UntagDoing } from "#steamside/application/untag/UntagDoing.js";
import { reentry_as_SomethingWentWrong } from "#steamside/application/SomethingWentWrong_reentry.js";
export class UntagRequest {
    static async on_UntagPleaseEvent(event, options) {
        const { fun, tagName, funName } = event.detail;
        const endpoint = "api/collection/" + tagName + "/remove/" + fun.id;
        const dryRun = options.dryRun;
        const backend = new BackendBridge(dryRun ? { dryRun: true } : undefined);
        const switchboard = event.currentTarget;
        Skyward.orbit(switchboard, {
            type: UntagDoing.eventType,
            detail: { fun, funName, tagName, dryRun, endpoint }
        });
        try {
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(endpoint);
        }
        catch (err) {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally {
            Skyward.orbit(switchboard, { type: UntagDone.eventType, detail: { fun, tagName } });
        }
    }
}
//# sourceMappingURL=UntagRequest.js.map