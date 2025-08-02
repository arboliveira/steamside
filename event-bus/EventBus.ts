import {EventBusSubscribePlease} from "#steamside/event-bus/EventBusSubscribePlease.js";
import {EventBusUnsubscribePlease} from "#steamside/event-bus/EventBusUnsubscribePlease.js";
import {Subscription} from "#steamside/event-bus/EventBusSubscription.js";

export class EventBus {
    constructor(
        private readonly options: {switchboard: EventTarget},
    ) {
    }
    // TODO investigate why document does not play well with Skyward.orbit ("Now Playing" goes to world element)
    // private readonly switchboard: EventTarget = this.options.switchboard ?? document;
    private readonly switchboard: EventTarget = this.options.switchboard;

    subscribe(...subscriptions: Array<Subscription>) {
        for (const subscription of subscriptions) {
            this.switchboard.addEventListener(subscription.type, subscription.listener);
        }
    }

    unsubscribe(...subscriptions: Array<Subscription>) {
        for (const subscription of subscriptions) {
            this.switchboard.removeEventListener(subscription.type, subscription.listener);
        }
    }

    on_connected() {
        this.subscribe(...this.subscriptions);
    }

    on_disconnected() {
        this.unsubscribe(...this.subscriptions);
    }

    private readonly subscriptions: Array<Subscription> = [
        {
            type: EventBusSubscribePlease.eventType,
            listener: event => this.#on_SubscribePlease(<CustomEvent>event),
        },
        {
            type: EventBusUnsubscribePlease.eventType,
            listener: event => this.#on_UnsubscribePlease(<CustomEvent>event),
        },
    ];

    #on_SubscribePlease({detail}: CustomEvent<EventBusSubscribePlease.EventDetail>) {
        this.subscribe(...detail.subscriptions);
    }

    #on_UnsubscribePlease({detail}: CustomEvent<EventBusUnsubscribePlease.EventDetail>) {
        this.unsubscribe(...detail.subscriptions);
    }
}
