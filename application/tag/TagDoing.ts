import {Fun} from "#steamside/application/Fun";

export namespace TagDoing {
    class TagDoing {}
    export const eventType: string = `steamside:${TagDoing.name}`;

    export type EventDetail = {
        fun: Fun,
        funName: string,
        tagName: string,
        endpoint: string,
        dryRun: boolean,
    }
}
