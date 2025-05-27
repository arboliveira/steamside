export namespace PlayError {
    export const eventType: string = "steamside:PlayError";

    export type EventDetail = {
        error: unknown;
    }
}
