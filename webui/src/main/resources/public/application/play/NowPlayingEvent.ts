import {Fun} from "#steamside/application/Fun.js";

export namespace NowPlaying {
    export const eventType: string = 'steamside:NowPlaying';

    export type EventDetail = {
        fun: Fun;
        funName: string;
        fun_endpoint: string;
        dryRun: boolean;
    }
}
