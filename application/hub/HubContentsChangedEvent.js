export class HubContentsChangedEvent extends CustomEvent {
    static { this.eventName = 'steamside:collection-contents-changed'; }
    constructor(detail) {
        super(HubContentsChangedEvent.eventName, {
            detail,
            bubbles: false,
        });
    }
}
//# sourceMappingURL=HubContentsChangedEvent.js.map