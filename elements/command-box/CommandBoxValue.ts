import {event} from "#steamside/application/names/event.js";

export namespace CommandBoxValue {
    class CommandBoxValue {}
    export const eventTypeChanged: string = event.typeChanged(CommandBoxValue.name);
    export type ChangedDetail = {input_text_command_box_value: string};
}
