import {Skyward} from "#steamside/event-bus/Skyward.js";
import {GameOver} from "#steamside/application/play/GameOverEvent.js";
import {NowPlaying} from "#steamside/application/play/NowPlayingEvent.js";
import {BackendBridge} from "#steamside/application/BackendBridge.js";
import {PlayPlease} from "#steamside/application/play/PlayPleaseEvent.js";
import {reentry_as_SomethingWentWrong} from "#steamside/application/SomethingWentWrong_reentry.js";

export class PlayRequest {
    static async on_PlayPleaseEvent(
        event: CustomEvent<PlayPlease.EventDetail>,
        options: {dryRun: boolean, backend?: BackendBridge},
    ) {
        const {fun, funName, fun_endpoint} = event.detail;

        const dryRun = options.dryRun;

        const backend: BackendBridge =
            options.backend ??
            new BackendBridge(dryRun ? {dryRun: true, sleep: 3000} : undefined);

        const switchboard: EventTarget = event.currentTarget!;
        
        Skyward.orbit<NowPlaying.EventDetail>(
            switchboard,
            {
                type: NowPlaying.eventType,
                detail: {fun, funName, fun_endpoint, dryRun}
            }
        );
        try
        {
            // FIXME POST: Not a read, not idempotent, has side effects
            await backend.fetch(fun_endpoint);
        }
        catch (err)
        {
            reentry_as_SomethingWentWrong(event, err);
        }
        finally {
            Skyward.orbit<GameOver.EventDetail>(
                switchboard, {type: GameOver.eventType, detail: {fun}});
        }
    }
}
