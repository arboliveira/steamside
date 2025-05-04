export type NowPlayingDetail = {
    appid: string;
    toast_content?: string;
}

export class NowPlayingEvent extends CustomEvent<NowPlayingDetail> {
    public static readonly eventName: string = 'steamside:now-playing';

    constructor(detail: NowPlayingDetail) {
        super(
            NowPlayingEvent.eventName, {
                detail,
                bubbles: true,
                composed: true,
            }
        );
    }
}