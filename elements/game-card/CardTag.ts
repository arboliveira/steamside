import {event} from "#steamside/application/names/event.js";
import {CardView} from "#steamside/elements-game-card-steamside.js";

export namespace CardTag {

    export class CardTag {}
    export const eventTypePlease: string = event.typePlease(CardTag.name);

    export type PleaseDetail = {
        card: CardView;
    }
}
