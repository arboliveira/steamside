import {Fun} from "#steamside/application/Fun.js";

export namespace GameOver {
    class GameOver {}

    export const eventType: string = `steamside:${GameOver.name}`;

    export type EventDetail = {
        fun: Fun;
    }
}
