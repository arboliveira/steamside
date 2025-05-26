import {Skyward} from "#steamside/event-bus/Skyward.js";
import {SomethingWentWrong} from "#steamside/application/SomethingWentWrong.js";

export function reentry_as_SomethingWentWrong(event: CustomEvent, err: unknown) {
    Skyward.reentry<SomethingWentWrong.EventDetail>(
      event, {
            type: SomethingWentWrong.eventType,
            detail: {
                error: err,
            }
        }
    );
}
