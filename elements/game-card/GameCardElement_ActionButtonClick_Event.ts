import {Game} from "#steamside/data-game";

export const GameCardElement_ActionButtonClick_eventName = 'GameCardElement:ActionButtonClick';

export type GameCardElement_ActionButtonClick_eventDetail = {
    action_button: string;
    game: Game;
    originator: Element;
}
