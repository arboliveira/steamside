import {event} from "#steamside/application/names/event.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {SessionHandshake} from "#steamside/application/session/SessionHandshake.js";
import {SessionData} from "#steamside/data-session.js";

export namespace KidsModeRead {
    class KidsModeRead {}
    export const eventTypePlease: string = event.typePlease(KidsModeRead.name);
    export const eventTypeDone: string = event.typeDone(KidsModeRead.name);
    export type DoneDetail = {kidsMode: boolean};

    export class Command {
        constructor(private readonly sessionHandshake: SessionHandshake) {}

        async execute(event: Event) {
            const sessionData: SessionData = await this.sessionHandshake.getSessionData();
            const kidsMode: boolean = sessionData.kidsMode;
            Skyward.reentry<KidsModeRead.DoneDetail>(event, {
                type: eventTypeDone,
                detail: {kidsMode},
            });
        }
    }
}
