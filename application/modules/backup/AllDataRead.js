import { event } from "#steamside/application/names/event.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
export var AllDataRead;
(function (AllDataRead_1) {
    class AllDataRead {
    }
    AllDataRead_1.eventTypePlease = event.typePlease(AllDataRead.name);
    AllDataRead_1.eventTypeDone = event.typeDone(AllDataRead.name);
    class Command {
        constructor(readOperations) {
            this.readOperations = readOperations;
        }
        async execute(event) {
            const data = this.readOperations.allData();
            Skyward.reentry(event, {
                type: AllDataRead_1.eventTypeDone,
                detail: { data },
            });
        }
    }
    AllDataRead_1.Command = Command;
})(AllDataRead || (AllDataRead = {}));
//# sourceMappingURL=AllDataRead.js.map