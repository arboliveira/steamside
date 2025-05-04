import {imagineDryRun} from "#steamside/data-offline-mode.js";
import {AppTagPleaseEvent} from "#steamside/requests/app/AppTagPleaseEvent.js";
import {AppTagDoneEvent} from "#steamside/requests/app/AppTagDoneEvent.js";

export class AppTagRequest {
    static async tagApp(
        {event, dryRun}:
        {
            event: AppTagPleaseEvent,
            dryRun: boolean,
        }
    ) {
        const {collection, game, originator} = event.detail;

        const url = "api/app/" + game.appid + "/tag/" + encodeURIComponent(collection);

        if (!dryRun) {
            // FIXME POST
            await fetch(url);
        }

        originator.dispatchEvent(new AppTagDoneEvent({
            game,
            collection,
            toast_content: imagineDryRun({imagine: `${game.name} was added to ${collection}`, url, dryRun}),
        }));
    }
}