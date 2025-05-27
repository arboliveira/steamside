import {Subscription} from "#steamside/event-bus/EventBusSubscription.js";

export namespace EventBusUnsubscribePlease {
    class EventBusUnsubscribe {}

    export const eventType: string = `steamside:${EventBusUnsubscribe.name}`;

    export type EventDetail = {
        subscriptions: Array<Subscription>;
    }
}
