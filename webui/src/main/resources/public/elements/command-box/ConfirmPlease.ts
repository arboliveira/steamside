export namespace ConfirmPlease {
    class ConfirmPlease {}
    export const eventType = `steamside:${ConfirmPlease.name}`;

    export type EventDetail = {
        input_text_command_box_value: string;
    }
}
