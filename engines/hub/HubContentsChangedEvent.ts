type HubContentsChangedEventDetail = {
    tagName: string,
}

export class HubContentsChangedEvent extends CustomEvent<HubContentsChangedEventDetail> {
    public static readonly eventName: string = 'steamside:collection-contents-changed';

    constructor(detail: HubContentsChangedEventDetail) {
        super(HubContentsChangedEvent.eventName, {
            detail,
            bubbles: false,
        });
    }
}
