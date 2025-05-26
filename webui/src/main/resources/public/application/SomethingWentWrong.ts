export namespace SomethingWentWrong {
    class SomethingWentWrong {}
    export const eventType = `steamside:${SomethingWentWrong.name}`;

    export type EventDetail = {
        error: unknown;
    };
}
