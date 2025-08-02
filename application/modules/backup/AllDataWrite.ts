import {event} from "#steamside/application/names/event.js";
import {ReadOperations} from "#steamside/application/localfirst/ReadOperations.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {Data} from "#steamside/application/localfirst/Data.js";
import {WriteOperations} from "#steamside/application/localfirst/WriteOperations";

export namespace AllDataWrite {
    class AllDataWrite {}
    export const eventTypePlease: string = event.typePlease(AllDataWrite.name);
    export const eventTypeDone: string = event.typeDone(AllDataWrite.name);
    export type PleaseDetail = {data: Data};

    export class Command {
        constructor(private readonly writeOperations: WriteOperations) {}

        async execute(event: CustomEvent<PleaseDetail>)
        {
            this.writeOperations.allData(event.detail.data);

            Skyward.reentry(event, {type: eventTypeDone});
        }
    }
}
