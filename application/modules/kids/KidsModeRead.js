import { event } from "#steamside/application/names/event.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
export var KidsModeRead;
(function (KidsModeRead_1) {
    class KidsModeRead {
    }
    KidsModeRead_1.eventTypePlease = event.typePlease(KidsModeRead.name);
    KidsModeRead_1.eventTypeDone = event.typeDone(KidsModeRead.name);
    class Command {
        constructor(sessionHandshake) {
            this.sessionHandshake = sessionHandshake;
        }
        async execute(event) {
            const sessionData = await this.sessionHandshake.getSessionData();
            const kidsMode = sessionData.kidsMode;
            Skyward.reentry(event, {
                type: KidsModeRead_1.eventTypeDone,
                detail: { kidsMode },
            });
        }
    }
    KidsModeRead_1.Command = Command;
})(KidsModeRead || (KidsModeRead = {}));
//# sourceMappingURL=KidsModeRead.js.map