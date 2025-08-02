import { event } from "#steamside/application/names/event.js";
import { reentry_as_SomethingWentWrong } from "#steamside/application/SomethingWentWrong_reentry.js";
export var WallpaperSettingsWrite;
(function (WallpaperSettingsWrite_1) {
    class WallpaperSettingsWrite {
    }
    WallpaperSettingsWrite_1.eventTypePlease = event.typePlease(WallpaperSettingsWrite.name);
    class Command {
        constructor(writeOperations) {
            this.writeOperations = writeOperations;
        }
        execute(event, options) {
            const { wallpaperSettings } = event.detail;
            const dryRun = options.dryRun;
            /*
            Skyward.orbit<WallpapersSave.Doing.EventDetail>(
                event, {type: WallpapersSave.Doing.eventType, detail: {wallpapers}});
             */
            try {
                this.writeOperations.wallpaperSettings(wallpaperSettings);
            }
            catch (err) {
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
    WallpaperSettingsWrite_1.Command = Command;
})(WallpaperSettingsWrite || (WallpaperSettingsWrite = {}));
//# sourceMappingURL=WallpaperSettingsWrite.js.map