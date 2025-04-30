import {Game} from "#steamside/data-game.js";

type TagPleaseEventDetail = {
    game: Game,
    tagName: string,
    originator: Element
}

export class TagPleaseEvent extends CustomEvent<TagPleaseEventDetail> {
    public static readonly eventName: string = 'steamside:tag-please';

    constructor(detail: TagPleaseEventDetail) {
        super(TagPleaseEvent.eventName, {
            detail,
            composed: true,
            bubbles: true,
        });
    }
}
