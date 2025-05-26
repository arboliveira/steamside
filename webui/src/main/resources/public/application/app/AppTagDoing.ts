import {Fun} from "#steamside/application/Fun.js";

export namespace AppTagDoing {
    class AppTagDoing {}
    export const eventType: string = `steamside:${AppTagDoing.name}`;

    export type EventDetail = {
        fun: Fun,
        collection: string,
        endpoint: string,
        dryRun: boolean,
    }
}
