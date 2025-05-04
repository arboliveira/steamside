export class TagDoneEvent extends CustomEvent {
    static { this.eventName = 'steamside:tag-done'; }
    constructor(detail) {
        super(TagDoneEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}
//# sourceMappingURL=TagDoneEvent.js.map