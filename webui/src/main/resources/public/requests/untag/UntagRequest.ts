import {UntagDoneEvent} from "#steamside/requests/untag/UntagDoneEvent.js";
import {imagineDryRun} from "#steamside/data-offline-mode.js";
import {UntagPleaseEvent} from "#steamside/requests/untag/UntagPleaseEvent";

export class UntagRequest {
    static async removeGameFromInventory(
        {
            event,
            dryRun
        }:
        {
            event: UntagPleaseEvent,
            dryRun: boolean,
        }
    ) {
        const {tagName, game, originator} = event.detail;

        const url = "api/collection/" + tagName + "/remove/" + game.appid;

        if (!dryRun) {
            // FIXME POST
            await fetch(url);
        }

        originator.dispatchEvent(new UntagDoneEvent({
            game,
            tagName,
            toast_content: imagineDryRun({imagine: `${game.name} was removed from ${tagName}`, url, dryRun}),
        }));
    }
}