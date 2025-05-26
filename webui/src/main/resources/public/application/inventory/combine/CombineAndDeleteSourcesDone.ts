export namespace CombineAndDeleteSourcesDone {
    class CombineAndDeleteSourcesDone {}
    export const eventType = `steamside:${CombineAndDeleteSourcesDone.name}`;

    export type EventDetail = {
        inventory_editing_alias: string,
        inventory_combining_alias: string,
        inventory_destination: string,
    }
}
