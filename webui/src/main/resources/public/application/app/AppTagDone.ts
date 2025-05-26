import {Fun} from "#steamside/application/Fun.js";

export namespace AppTagDone {
    class AppTagDone {}
    export const eventType: string = `steamside:${AppTagDone.name}`;

    export type EventDetail = {
        fun: Fun,
        collection: string,
    }
}
