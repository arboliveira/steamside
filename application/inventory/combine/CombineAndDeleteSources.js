import { BackendBridge } from "#steamside/application/BackendBridge.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
import { CombineAndDeleteSourcesDone } from "#steamside/application/inventory/combine/CombineAndDeleteSourcesDone.js";
import { CombineAndDeleteSourcesDoing } from "#steamside/application/inventory/combine/CombineAndDeleteSourcesDoing.js";
import { reentry_as_SomethingWentWrong } from "#steamside/application/SomethingWentWrong_reentry.js";
export class CombineAndDeleteSources {
    static async on_CombineAndDeleteSourcesPlease(event, options) {
        const { inventory_editing_alias, inventory_combining_alias, inventory_destination } = event.detail;
        const endpoint = "api/collection/" +
            encodeURIComponent(inventory_editing_alias) +
            "/combine/" +
            encodeURIComponent(inventory_combining_alias) +
            "/into/" +
            encodeURIComponent(inventory_destination) +
            "/move";
        const dryRun = options.dryRun;
        const backend = new BackendBridge(dryRun ? { dryRun: true } : undefined);
        Skyward.orbit(event, {
            type: CombineAndDeleteSourcesDoing.eventType,
            detail: {
                inventory_destination,
                inventory_combining_alias,
                inventory_editing_alias,
                dryRun,
                endpoint,
            }
        });
        try {
            if (!dryRun) {
                const DID_YOU_WRITE_TESTS_ALREADY = false;
                if (!DID_YOU_WRITE_TESTS_ALREADY) {
                    throw new Error('Danger zone, write tests first');
                }
            }
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(endpoint);
        }
        catch (err) {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally {
            Skyward.orbit(event, { type: CombineAndDeleteSourcesDone.eventType, detail: {
                    inventory_destination, inventory_combining_alias, inventory_editing_alias
                } });
        }
    }
}
//# sourceMappingURL=CombineAndDeleteSources.js.map