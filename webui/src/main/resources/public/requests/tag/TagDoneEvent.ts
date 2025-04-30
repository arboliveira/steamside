import {Game} from "#steamside/data-game.js";

type TagDoneEventDetail = {
    game: Game,
    tagName: string,
    toast_content?: string,
}

export class TagDoneEvent extends CustomEvent<TagDoneEventDetail> {
    public static readonly eventName: string = 'steamside:tag-done';

    constructor(detail: TagDoneEventDetail) {
        super(TagDoneEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}