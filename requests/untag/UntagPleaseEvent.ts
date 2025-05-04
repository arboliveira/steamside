import {Game} from "#steamside/data-game.js";

type UntagPleaseEventDetail = {
    game: Game,
    tagName: string,
    originator: Element
}

export class UntagPleaseEvent extends CustomEvent<UntagPleaseEventDetail> {
    public static readonly eventName: string = 'steamside:untag-please';

    constructor(detail: UntagPleaseEventDetail) {
        super(UntagPleaseEvent.eventName, {
            detail,
            composed: true,
            bubbles: true,
        });
    }
}
