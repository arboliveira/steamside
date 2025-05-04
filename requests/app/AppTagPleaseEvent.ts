import {Game} from "#steamside/data-game.js";

type AppTagPleaseEventDetail = {
    game: Game,
    collection: string,
    originator: Element
}

export class AppTagPleaseEvent extends CustomEvent<AppTagPleaseEventDetail> {
    public static readonly eventName: string = 'steamside:app-tag-please';

    constructor(detail: AppTagPleaseEventDetail) {
        super(AppTagPleaseEvent.eventName, {
            detail,
            composed: true,
            bubbles: true,
        });
    }
}
