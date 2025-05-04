import { TagDoneEvent } from "#steamside/requests/tag/TagDoneEvent.js";
import { imagineDryRun } from "#steamside/data-offline-mode.js";
export class TagRequest {
    static async addGameToInventory({ event, dryRun }) {
        const { tagName, game, originator } = event.detail;
        const url = "api/collection/" + tagName + "/add/" + game.appid;
        if (!dryRun) {
            // FIXME POST
            await fetch(url);
        }
        originator.dispatchEvent(new TagDoneEvent({
            game,
            tagName,
            toast_content: imagineDryRun({ imagine: `${game.name} was added to ${tagName}`, url, dryRun }),
        }));
    }
}
//# sourceMappingURL=TagRequest.js.map