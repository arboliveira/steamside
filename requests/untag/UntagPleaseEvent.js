export class UntagPleaseEvent extends CustomEvent {
    static { this.eventName = 'steamside:untag-please'; }
    constructor(detail) {
        super(UntagPleaseEvent.eventName, {
            detail,
            composed: true,
            bubbles: true,
        });
    }
}
//# sourceMappingURL=UntagPleaseEvent.js.map