export class TagPleaseEvent extends CustomEvent {
    static { this.eventName = 'steamside:tag-please'; }
    constructor(detail) {
        super(TagPleaseEvent.eventName, {
            detail,
            composed: true,
            bubbles: true,
        });
    }
}
//# sourceMappingURL=TagPleaseEvent.js.map