import {Subscription} from "#steamside/event-bus/EventBusSubscription.js";
import {EventBusSubscribePlease} from "#steamside/event-bus/EventBusSubscribePlease.js";
import {EventBusUnsubscribePlease} from "#steamside/event-bus/EventBusUnsubscribePlease.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";

export namespace EventBusSubscribeOnConnected {
    export function subscribe(subscriber: EventTarget, subscriptions: Array<Subscription>) {
        Skyward.fly<EventBusSubscribePlease.EventDetail>(subscriber, {
            type: EventBusSubscribePlease.eventType,
            detail: {subscriptions},
        });
    }
}

export namespace EventBusUnsubscribeOnDisconnected {
    export function unsubscribe(subscriber: EventTarget, subscriptions: Array<Subscription>) {
        Skyward.fly<EventBusUnsubscribePlease.EventDetail>(subscriber, {
            type: EventBusUnsubscribePlease.eventType,
            detail: {subscriptions},
        });
    }
}
