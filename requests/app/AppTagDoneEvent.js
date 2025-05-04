export class AppTagDoneEvent extends CustomEvent {
    static { this.eventName = 'steamside:app-tag-done'; }
    constructor(detail) {
        super(AppTagDoneEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}
//# sourceMappingURL=AppTagDoneEvent.js.map