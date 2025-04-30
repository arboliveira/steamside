type PlayPleaseEventDetail = {
    appid: string;
    url: string;
    originator: Element;
}

export class PlayPleaseEvent extends CustomEvent<PlayPleaseEventDetail> {
    public static readonly eventName: string = 'steamside:play-please';

    constructor(detail: PlayPleaseEventDetail) {
        super(
            PlayPleaseEvent.eventName, {
                detail,
                bubbles: true,
                composed: true,
            }
        );
    }
}
