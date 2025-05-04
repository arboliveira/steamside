export class NowPlayingEvent extends CustomEvent {
    static { this.eventName = 'steamside:now-playing'; }
    constructor(detail) {
        super(NowPlayingEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}
//# sourceMappingURL=NowPlayingEvent.js.map