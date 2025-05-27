export namespace Skyward {
    /**
     * Dispatches a CustomEvent with the given type and detail to the target.
     * The event bubbles and is composed, allowing it to cross shadow DOM boundaries.
     * The event is also tagged with the originating target for reentry purposes.
     * Useful for application events meant to be handled all the way up in the switchboard, where backend lives.
     */
    export function fly<T>(
        target: EventTarget,
        {type, detail} : {
            type: string,
            detail?: T,
        }
    ): boolean {
        return target.dispatchEvent(
            Object.assign(
                new CustomEvent(type, {detail, bubbles: true, composed: true}),
                {originatingTarget: target}
            )
        );
    }

    /**
     * Stops the given event and dispatches a new CustomEvent with the given type and detail to the target.
     * Useful to turn clicks and other UI events into application events meant for the switchboard.
     */
    export function stage<T>(
        event: Event,
        target: EventTarget,
        {type, detail} : {
            type: string,
            detail?: T,
        }
    ): boolean {
        event.stopPropagation();
        event.preventDefault();
        return fly(target, {type, detail});
    }

    /**
     * Dispatches a new CustomEvent to the same target as the given event.
     * Intended for application handlers, where target has already become the top level switchboard itself.
     * Useful while handling application events, to emit granular progress events meant for event bus subscribers.
     */
    export function orbit<T>(
        event: Event,
        {type, detail} : {
            type: string,
            detail?: T,
        }
    ) {
        const target = event.target;
        if (!target) {
            throw new Error("Event target was supposed to already be the event bus switchboard");
        }
        return target.dispatchEvent(new CustomEvent(type, {detail}));
    }

    /**
     * Dispatches a new CustomEvent to the originating target of the given event (as dispatched by "fly").
     * Useful to return info only to the UI element initially interacted with, e.g. to display errors.
     */
    export function reentry<T>(
        event: Event,
        {type, detail} : {
            type: string,
            detail?: T,
        }
    ) {
        const target: EventTarget = (event as any).originatingTarget;
        if (!target) {
            throw new Error("No originating target found for event reentry");
        }
        return target.dispatchEvent(new CustomEvent(type, {detail}));
    }
}
