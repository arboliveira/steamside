export type GameOverEventDetail = {
    appid: string;
}

export class GameOverEvent extends CustomEvent<GameOverEventDetail> {
    public static readonly eventName: string = 'steamside:game-over';

    constructor(detail: GameOverEventDetail) {
        super(
            GameOverEvent.eventName, {
                detail,
                bubbles: true,
                composed: true,
            }
        );
    }
}