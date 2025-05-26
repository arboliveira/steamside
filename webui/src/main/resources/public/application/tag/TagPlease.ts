import {Fun} from "#steamside/application/Fun";

export namespace TagPlease {
    class TagPlease {}
    export const eventType: string = `steamside:${TagPlease.name}`;

    export type EventDetail = {
        fun: Fun,
        funName: string,
        tagName: string,
    }
}
