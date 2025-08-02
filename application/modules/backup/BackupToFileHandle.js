import { event } from "#steamside/application/names/event.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
export var BackupToFileHandle;
(function (BackupToFileHandle_1) {
    class BackupToFileHandle {
    }
    BackupToFileHandle_1.eventTypePlease = event.typePlease(BackupToFileHandle.name);
    BackupToFileHandle_1.eventTypeDone = event.typeDone(BackupToFileHandle.name);
    class Command {
        constructor(readOperations) {
            this.readOperations = readOperations;
        }
        async execute(event) {
            const data = this.readOperations.allData();
            /*
            const backup = JSON.stringify(data, null, 2);

            const writableFileStream = await event.detail.fileHandle.createWritable();
            try
            {
                await writableFileStream.write(backup);
            }
            finally
            {
                await writableFileStream.close();
            }

            const savedToLocation = event.detail.fileHandle.name;
             */
            Skyward.reentry(event, {
                type: BackupToFileHandle_1.eventTypeDone,
                detail: { data },
            });
        }
    }
    BackupToFileHandle_1.Command = Command;
})(BackupToFileHandle || (BackupToFileHandle = {}));
//# sourceMappingURL=BackupToFileHandle.js.map