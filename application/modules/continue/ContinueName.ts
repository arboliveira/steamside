import {event} from "#steamside/application/names/event.js";

export namespace ContinueName {
    class ContinueName {}
    export const eventTypeChanged = event.typeChanged(ContinueName.name);

    export type EventDetail = {
        lastPlayed: number;
        name: string;
    }
}
