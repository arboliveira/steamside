import {event} from "#steamside/application/names/event.js";
import {ReadOperations} from "#steamside/application/localfirst/ReadOperations.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {WallpaperSettings} from "#steamside/application/modules/settings/WallpaperSettings.js";

export namespace WallpaperSettingsRead {
    class WallpaperSettingsRead {}
    export const eventTypePlease: string = event.typePlease(WallpaperSettingsRead.name);
    export const eventTypeDone: string = event.typeDone(WallpaperSettingsRead.name);
    export type DoneDetail = {wallpaperSettings?: WallpaperSettings};

    export class Command {
        constructor(private readonly readOperations: ReadOperations) {}

        execute(event: Event)
        {
            const wallpaperSettings = this.readOperations.wallpaperSettings();
            Skyward.reentry<WallpaperSettingsRead.DoneDetail>(event, {
                type: eventTypeDone,
                detail: {wallpaperSettings},
            });
        }
    }
}
