export namespace CombineAndDeleteSourcesDoing {
    class CombineAndDeleteSourcesDoing {}
    export const eventType = `steamside:${CombineAndDeleteSourcesDoing.name}`;

    export type EventDetail = {
        inventory_editing_alias: string,
        inventory_combining_alias: string,
        inventory_destination: string,
        dryRun: boolean,
        endpoint: string,
    }
}
