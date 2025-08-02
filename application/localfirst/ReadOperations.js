import { KEY } from "#steamside/application/localfirst/key.js";
export class ReadOperations {
    constructor(storage) {
        this.storage = storage;
    }
    allData() {
        return this.read(data => data);
    }
    wallpaperSettings() {
        return this.read(data => {
            return {
                enable_wallpaper: data.settings?.wallpaper?.enable_wallpaper,
                wallpapers: data.settings?.wallpaper?.wallpapers,
                preserve_aspect_ratio: data.settings?.wallpaper?.preserve_aspect_ratio,
            };
        });
    }
    read(readFn) {
        const item = this.storage.getItem(KEY);
        const data = item === null ? {} : JSON.parse(item);
        return readFn(data);
    }
}
//# sourceMappingURL=ReadOperations.js.map