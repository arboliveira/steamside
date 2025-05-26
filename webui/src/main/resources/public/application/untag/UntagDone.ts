import {Fun} from "#steamside/application/Fun.js";

export namespace UntagDone {
    class UntagDone {}
    export const eventType: string = `steamside:${UntagDone.name}`;

    export type EventDetail = {
        fun: Fun,
        tagName: string,
    }
}
