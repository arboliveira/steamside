import { EventBusSubscribePlease } from "#steamside/event-bus/EventBusSubscribePlease.js";
import { EventBusUnsubscribePlease } from "#steamside/event-bus/EventBusUnsubscribePlease.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
export var EventBusSubscribeOnConnected;
(function (EventBusSubscribeOnConnected) {
    function subscribe(subscriber, subscriptions) {
        Skyward.fly(subscriber, {
            type: EventBusSubscribePlease.eventType,
            detail: { subscriptions },
        });
    }
    EventBusSubscribeOnConnected.subscribe = subscribe;
})(EventBusSubscribeOnConnected || (EventBusSubscribeOnConnected = {}));
export var EventBusUnsubscribeOnDisconnected;
(function (EventBusUnsubscribeOnDisconnected) {
    function unsubscribe(subscriber, subscriptions) {
        Skyward.fly(subscriber, {
            type: EventBusUnsubscribePlease.eventType,
            detail: { subscriptions },
        });
    }
    EventBusUnsubscribeOnDisconnected.unsubscribe = unsubscribe;
})(EventBusUnsubscribeOnDisconnected || (EventBusUnsubscribeOnDisconnected = {}));
//# sourceMappingURL=EventBusSubscribe.js.map