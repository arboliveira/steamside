import {Fun} from "#steamside/application/Fun.js";

export namespace PlayPlease {
    class PlayPlease {}

    export const eventType: string = `steamside:${PlayPlease.name}`;

    export type EventDetail = { fun: Fun, funName: string, fun_endpoint: string };
}
