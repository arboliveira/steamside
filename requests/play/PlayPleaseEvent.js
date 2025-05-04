export class PlayPleaseEvent extends CustomEvent {
    static { this.eventName = 'steamside:play-please'; }
    constructor(detail) {
        super(PlayPleaseEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}
//# sourceMappingURL=PlayPleaseEvent.js.map