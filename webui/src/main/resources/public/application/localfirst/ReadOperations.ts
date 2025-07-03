import {StorageBridge} from "#steamside/application/localfirst/StorageBridge.js";
import {Data} from "#steamside/application/localfirst/Data.js";
import {KEY} from "#steamside/application/localfirst/key.js";
import {WallpaperSettings} from "#steamside/application/modules/settings/WallpaperSettings.js";

export class ReadOperations {
    constructor(private readonly storage: StorageBridge) {}

    allData(): Data {
        return this.read(data => data);
    }

    wallpaperSettings(): WallpaperSettings | undefined {
        return this.read(data => {
            return {
                enable_wallpaper: data.settings?.wallpaper?.enable_wallpaper,
                wallpapers: data.settings?.wallpaper?.wallpapers,
                preserve_aspect_ratio: data.settings?.wallpaper?.preserve_aspect_ratio,
            };
        });
    }

    private read<T>(readFn: ReadFn<T>): T
    {
        const item: string | null = this.storage.getItem(KEY);
        const data: Data = item === null ? {} : JSON.parse(item);
        return readFn(data);
    }
}

type ReadFn<T> = (data: Data) => T;
