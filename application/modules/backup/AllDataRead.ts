import {event} from "#steamside/application/names/event.js";
import {ReadOperations} from "#steamside/application/localfirst/ReadOperations.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {Data} from "#steamside/application/localfirst/Data.js";

export namespace AllDataRead {
    class AllDataRead {}
    export const eventTypePlease: string = event.typePlease(AllDataRead.name);
    export const eventTypeDone: string = event.typeDone(AllDataRead.name);
    export type DoneDetail = {data: Data};

    export class Command {
        constructor(private readonly readOperations: ReadOperations) {}

        async execute(event: Event)
        {
            const data: Data = this.readOperations.allData();

            Skyward.reentry<DoneDetail>(event, {
                type: eventTypeDone,
                detail: {data},
            });
        }
    }
}
