export namespace CombineAndDeleteSourcesPlease {
    class CombineAndDeleteSourcesPlease {}
    export const eventType = `steamside:${CombineAndDeleteSourcesPlease.name}`;

    export type EventDetail = {
        inventory_editing_alias: string,
        inventory_combining_alias: string,
        inventory_destination: string,
    }
}
