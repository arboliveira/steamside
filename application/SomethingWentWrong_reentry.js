import { Skyward } from "#steamside/event-bus/Skyward.js";
import { SomethingWentWrong } from "#steamside/application/SomethingWentWrong.js";
export function reentry_as_SomethingWentWrong(event, err) {
    Skyward.reentry(event, {
        type: SomethingWentWrong.eventType,
        detail: {
            error: err,
        }
    });
}
//# sourceMappingURL=SomethingWentWrong_reentry.js.map