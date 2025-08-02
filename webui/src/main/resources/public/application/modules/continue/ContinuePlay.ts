import {event} from "#steamside/application/names/event.js";

export namespace ContinuePlay {
    class ContinuePlay {}
    export const eventTypePlease = event.typePlease(ContinuePlay.name);

    export type EventDetail = {
        lastPlayed: number;
    }
}
