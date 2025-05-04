import { imagineDryRun } from "#steamside/data-offline-mode.js";
import { AppTagDoneEvent } from "#steamside/requests/app/AppTagDoneEvent.js";
export class AppTagRequest {
    static async tagApp({ event, dryRun }) {
        const { collection, game, originator } = event.detail;
        const url = "api/app/" + game.appid + "/tag/" + encodeURIComponent(collection);
        if (!dryRun) {
            // FIXME POST
            await fetch(url);
        }
        originator.dispatchEvent(new AppTagDoneEvent({
            game,
            collection,
            toast_content: imagineDryRun({ imagine: `${game.name} was added to ${collection}`, url, dryRun }),
        }));
    }
}
//# sourceMappingURL=AppTagRequest.js.map