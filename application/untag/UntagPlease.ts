import {Fun} from "#steamside/application/Fun.js";

export namespace UntagPlease {
    class UntagPlease {}
    export const eventType = `steamside:${UntagPlease.name}`;

    export type EventDetail = {
        fun: Fun,
        funName: string,
        tagName: string,
    }
}
