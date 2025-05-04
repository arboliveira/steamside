import { NowPlayingEvent } from "#steamside/requests/play/NowPlayingEvent.js";
import { GameOverEvent } from "#steamside/requests/play/GameOverEvent.js";
import { imagineDryRun } from "#steamside/data-offline-mode.js";
export class PlayRequest {
    static async play({ event, dryRun, fetchFn, dryRunDelay = 3000 }) {
        const { url, appid, originator } = event.detail;
        originator.dispatchEvent(new NowPlayingEvent({
            appid,
            toast_content: imagineDryRun({ imagine: `you're playing`, dryRun, url })
        }));
        if (dryRun) {
            await new Promise(resolve => setTimeout(resolve, dryRunDelay));
        }
        else {
            if (fetchFn) {
                await fetchFn(url);
            }
            else {
                // FIXME POST? Not a read, not idempotent, has side effects
                await fetch(url);
            }
        }
        originator.dispatchEvent(new GameOverEvent({ appid }));
    }
}
//# sourceMappingURL=PlayRequest.js.map