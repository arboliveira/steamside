export class UntagDoneEvent extends CustomEvent {
    static { this.eventName = 'steamside:untagged'; }
    constructor(detail) {
        super(UntagDoneEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}
//# sourceMappingURL=UntagDoneEvent.js.map