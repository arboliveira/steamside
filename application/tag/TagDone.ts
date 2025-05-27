import {Fun} from "#steamside/application/Fun";

export namespace TagDone {
    class TagDone {}
    export const eventType: string = `steamside:${TagDone.name}`;

    export type EventDetail = {
        fun: Fun,
        tagName: string,
    }
}
