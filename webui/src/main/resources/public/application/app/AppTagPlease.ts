import {Fun} from "#steamside/application/Fun.js";

export namespace AppTagPlease {
    class AppTagPlease {}
    export const eventType: string = `steamside:${AppTagPlease.name}`;

    export type EventDetail = {
        fun: Fun,
        collection: string,
    }
}
