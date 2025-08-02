import {Data} from "#steamside/application/localfirst/Data.js";
import {StorageBridge} from "#steamside/application/localfirst/StorageBridge.js";
import {KEY} from "#steamside/application/localfirst/key.js";
import {WallpaperSettings} from "#steamside/application/modules/settings/WallpaperSettings";

export class WriteOperations {
    constructor(private readonly storage: StorageBridge) {}

    allData(data: Data) {
        this.write((_data: Data) => data);
    }

    wallpaperSettings(wallpaperSettings: WallpaperSettings): void {
        this.write((data: Data) => {
            const wallpaper = {
                enable_wallpaper: wallpaperSettings.enable_wallpaper,
                wallpapers: wallpaperSettings.wallpapers,
                preserve_aspect_ratio: wallpaperSettings.preserve_aspect_ratio,
            };

            if (data.settings) {
                data.settings.wallpaper = wallpaper;
            }
            else {
                data.settings = {wallpaper};
            }
        });
    }

    private write(writeFn: WriteFn) {
        const item: string | null = this.storage.getItem(KEY);
        const z: Data = item ? JSON.parse(item) : {} ;
        const a: Data = writeFn(z) ?? z;
        this.storage.setItem(KEY, JSON.stringify(a));
    }
}

type WriteFn = (data: Data) => Data | void;

