export class GameOverEvent extends CustomEvent {
    static { this.eventName = 'steamside:game-over'; }
    constructor(detail) {
        super(GameOverEvent.eventName, {
            detail,
            bubbles: true,
            composed: true,
        });
    }
}
//# sourceMappingURL=GameOverEvent.js.map