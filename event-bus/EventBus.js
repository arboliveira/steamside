import { EventBusSubscribePlease } from "#steamside/event-bus/EventBusSubscribePlease.js";
import { EventBusUnsubscribePlease } from "#steamside/event-bus/EventBusUnsubscribePlease.js";
export class EventBus {
    constructor(switchboard) {
        this.switchboard = switchboard;
        this.subscriptions = [
            {
                type: EventBusSubscribePlease.eventType,
                listener: event => this.#on_SubscribePlease(event),
            },
            {
                type: EventBusUnsubscribePlease.eventType,
                listener: event => this.#on_UnsubscribePlease(event),
            },
        ];
    }
    subscribe(...subscriptions) {
        for (const subscription of subscriptions) {
            this.switchboard.addEventListener(subscription.type, subscription.listener);
        }
    }
    unsubscribe(...subscriptions) {
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
    #on_SubscribePlease({ detail }) {
        this.subscribe(...detail.subscriptions);
    }
    #on_UnsubscribePlease({ detail }) {
        this.unsubscribe(...detail.subscriptions);
    }
}
//# sourceMappingURL=EventBus.js.map