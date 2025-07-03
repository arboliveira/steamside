import {event} from "#steamside/application/names/event.js";
import {reentry_as_SomethingWentWrong} from "#steamside/application/SomethingWentWrong_reentry.js";
import {WriteOperations} from "#steamside/application/localfirst/WriteOperations.js";
import {WallpaperSettings} from "#steamside/application/modules/settings/WallpaperSettings";

export namespace WallpaperSettingsWrite {
    class WallpaperSettingsWrite {}
    export const eventTypePlease: string = event.typePlease(WallpaperSettingsWrite.name);
    export type EventDetail = {
        wallpaperSettings: WallpaperSettings;
    }

    export class Command {
        constructor(private readonly writeOperations: WriteOperations) {}

        execute(event: CustomEvent<WallpaperSettingsWrite.EventDetail>, options: {dryRun: boolean}) {
            const {wallpaperSettings} = event.detail;

            const dryRun = options.dryRun;

            /*
            Skyward.orbit<WallpapersSave.Doing.EventDetail>(
                event, {type: WallpapersSave.Doing.eventType, detail: {wallpapers}});
             */
            try {
                this.writeOperations.wallpaperSettings(wallpaperSettings);
            } catch (err) {
                reentry_as_SomethingWentWrong(event, err);
            }
            /*
            finally
            {
                Skyward.orbit<WallpapersSave.Done.EventDetail>(
                    event, {type: WallpapersSave.Done.eventType, detail: {wallpapers}});
            }
             */
        }
    }
}
