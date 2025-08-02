import { KEY } from "#steamside/application/localfirst/key.js";
export class WriteOperations {
    constructor(storage) {
        this.storage = storage;
    }
    allData(data) {
        this.write((_data) => data);
    }
    wallpaperSettings(wallpaperSettings) {
        this.write((data) => {
            const wallpaper = {
                enable_wallpaper: wallpaperSettings.enable_wallpaper,
                wallpapers: wallpaperSettings.wallpapers,
                preserve_aspect_ratio: wallpaperSettings.preserve_aspect_ratio,
            };
            if (data.settings) {
                data.settings.wallpaper = wallpaper;
            }
            else {
                data.settings = { wallpaper };
            }
        });
    }
    write(writeFn) {
        const item = this.storage.getItem(KEY);
        const z = item ? JSON.parse(item) : {};
        const a = writeFn(z) ?? z;
        this.storage.setItem(KEY, JSON.stringify(a));
    }
}
//# sourceMappingURL=WriteOperations.js.map