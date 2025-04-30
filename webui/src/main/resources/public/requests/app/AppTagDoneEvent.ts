import {Game} from "#steamside/data-game.js";

type AppTagDoneEventDetail = {
    game: Game,
    collection: string,
    toast_content?: string,
}

export class AppTagDoneEvent extends CustomEvent<AppTagDoneEventDetail> {
    public static readonly eventName: string = 'steamside:app-tag-done';

    constructor(detail: AppTagDoneEventDetail) {
        super(AppTagDoneEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}