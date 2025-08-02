import { Skyward } from "#steamside/event-bus/Skyward.js";
import { GameOver } from "#steamside/application/play/GameOverEvent.js";
import { NowPlaying } from "#steamside/application/play/NowPlayingEvent.js";
import { BackendBridge } from "#steamside/application/BackendBridge.js";
import { reentry_as_SomethingWentWrong } from "#steamside/application/SomethingWentWrong_reentry.js";
export class PlayRequest {
    static async on_PlayPleaseEvent(event, options) {
        const { fun, funName, fun_endpoint } = event.detail;
        const dryRun = options.dryRun;
        const backend = options.backend ??
            new BackendBridge(dryRun ? { dryRun: true, sleep: 3000 } : undefined);
        const switchboard = event.currentTarget;
        Skyward.orbit(switchboard, {
            type: NowPlaying.eventType,
            detail: { fun, funName, fun_endpoint, dryRun }
        });
        try {
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(fun_endpoint);
        }
        catch (err) {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally {
            Skyward.orbit(switchboard, { type: GameOver.eventType, detail: { fun } });
        }
    }
}
//# sourceMappingURL=PlayRequest.js.map