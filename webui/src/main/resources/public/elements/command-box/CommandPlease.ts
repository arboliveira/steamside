export namespace CommandPlease {
    class CommandPlease {}
    export const eventType = `steamside:${CommandPlease.name}`;

    export type EventDetail = {
        input_text_command_box_value: string;
    }
}
