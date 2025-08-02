import { event } from "#steamside/application/names/event.js";
import { Skyward } from "#steamside/event-bus/Skyward.js";
export var WallpaperSettingsRead;
(function (WallpaperSettingsRead_1) {
    class WallpaperSettingsRead {
    }
    WallpaperSettingsRead_1.eventTypePlease = event.typePlease(WallpaperSettingsRead.name);
    WallpaperSettingsRead_1.eventTypeDone = event.typeDone(WallpaperSettingsRead.name);
    class Command {
        constructor(readOperations) {
            this.readOperations = readOperations;
        }
        execute(event) {
            const wallpaperSettings = this.readOperations.wallpaperSettings();
            Skyward.reentry(event, {
                type: WallpaperSettingsRead_1.eventTypeDone,
                detail: { wallpaperSettings },
            });
        }
    }
    WallpaperSettingsRead_1.Command = Command;
})(WallpaperSettingsRead || (WallpaperSettingsRead = {}));
//# sourceMappingURL=WallpaperSettingsRead.js.map