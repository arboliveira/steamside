export class AppTagPleaseEvent extends CustomEvent {
    static { this.eventName = 'steamside:app-tag-please'; }
    constructor(detail) {
        super(AppTagPleaseEvent.eventName, {
            detail,
            composed: true,
            bubbles: true,
        });
    }
}
//# sourceMappingURL=AppTagPleaseEvent.js.map