import {Game} from "#steamside/data-game.js";

export namespace GameCardTagPlease {
    export const eventType: string = 'steamside:GameCardTagPlease';

    export type EventDetail = {
        game: Game;
    }
}
