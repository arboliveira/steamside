import { event } from "#steamside/application/names/event.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
export var AllDataWrite;
(function (AllDataWrite_1) {
    class AllDataWrite {
    }
    AllDataWrite_1.eventTypePlease = event.typePlease(AllDataWrite.name);
    AllDataWrite_1.eventTypeDone = event.typeDone(AllDataWrite.name);
    class Command {
        constructor(writeOperations) {
            this.writeOperations = writeOperations;
        }
        async execute(event) {
            this.writeOperations.allData(event.detail.data);
            Skyward.reentry(event, { type: AllDataWrite_1.eventTypeDone });
        }
    }
    AllDataWrite_1.Command = Command;
})(AllDataWrite || (AllDataWrite = {}));
//# sourceMappingURL=AllDataWrite.js.map