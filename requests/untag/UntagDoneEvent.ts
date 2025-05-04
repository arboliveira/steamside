import {Game} from "#steamside/data-game.js";

type UntagDoneEventDetail = {
    game: Game,
    tagName: string,
    toast_content?: string,
}

export class UntagDoneEvent extends CustomEvent<UntagDoneEventDetail> {
    public static readonly eventName: string = 'steamside:untagged';

    constructor(detail: UntagDoneEventDetail) {
        super(UntagDoneEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}