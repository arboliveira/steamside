import {Game} from "#steamside/data-game.js";

export namespace GameActionButtonClick {
    export const eventType: string = 'steamside:GameActionButtonClick';

    export type EventDetail = {
        action_button: string;
        game: Game;
    }
}
