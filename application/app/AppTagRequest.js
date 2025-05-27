import { BackendBridge } from "#steamside/application/BackendBridge.js";
import { AppTagDone } from "#steamside/application/app/AppTagDone.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
import { reentry_as_SomethingWentWrong } from "#steamside/application/SomethingWentWrong_reentry.js";
import { AppTagDoing } from "#steamside/application/app/AppTagDoing.js";
export class AppTagRequest {
    static async on_AppTagPleaseEvent(event, options) {
        const { fun, collection } = event.detail;
        const endpoint = "api/app/" + fun.id + "/tag/" + encodeURIComponent(collection);
        const dryRun = options.dryRun;
        const backend = new BackendBridge(dryRun ? { dryRun: true } : undefined);
        Skyward.orbit(event, {
            type: AppTagDoing.eventType,
            detail: { fun, collection, endpoint, dryRun }
        });
        try {
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(endpoint);
        }
        catch (err) {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally {
            Skyward.orbit(event, { type: AppTagDone.eventType, detail: { fun, collection } });
        }
    }
}
//# sourceMappingURL=AppTagRequest.js.map