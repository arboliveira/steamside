import {Subscription} from "#steamside/event-bus/EventBusSubscription.js";

export namespace EventBusSubscribePlease {
    class EventBusSubscribe {}
    export const eventType: string = `steamside:${EventBusSubscribe.name}`;

    export type EventDetail = {
        subscriptions: Array<Subscription>;
    }
}
