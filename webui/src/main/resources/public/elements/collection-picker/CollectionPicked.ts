export namespace CollectionPicked {
    class CollectionPicked {}
    export const eventType = `steamside:${CollectionPicked.name}`;

    export type EventDetail = {
        tagName: string,
    }
}
