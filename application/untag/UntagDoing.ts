import {Fun} from "#steamside/application/Fun.js";

export namespace UntagDoing {
    class UntagDoing {}
    export const eventType: string = `steamside:${UntagDoing.name}`;

    export type EventDetail = {
        fun: Fun,
        funName: string,
        tagName: string,
        endpoint: string,
        dryRun: boolean,
    }
}
