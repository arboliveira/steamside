import {event} from "#steamside/application/names/event.js";

export namespace CommandPlease {
    class CommandPlease {}
    export const eventType = event.typePlease(CommandPlease.name);

    export type EventDetail = {
        input_text_command_box_value: string;
    }
}
