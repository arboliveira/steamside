export namespace CommandAlternatePlease {
    class CommandAlternatePlease {}
    export const eventType = `steamside:${CommandAlternatePlease.name}`;

    export type EventDetail = {
        input_text_command_box_value: string;
    }
}
