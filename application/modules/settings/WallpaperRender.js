export class WallpaperRender {
    static renderFirstWallpaper(wallpaperSettings) {
        function urlToUse() {
            const lines = wallpaperSettings.wallpapers?.split(/\r?\n/);
            const active = lines?.filter(s => s.length > 0 && !s.startsWith('#'));
            return active?.[0];
        }
        const url = wallpaperSettings.enable_wallpaper ? urlToUse() : undefined;
        const bodyStyle = document.body.style;
        if (url) {
            bodyStyle.backgroundImage = `url(${url})`;
        }
        else {
            bodyStyle.backgroundImage = '';
        }
        const size = (wallpaperSettings.enable_wallpaper && wallpaperSettings.preserve_aspect_ratio) ? 'contain' : undefined;
        bodyStyle.backgroundSize = size ? 'contain' : '';
    }
}
//# sourceMappingURL=WallpaperRender.js.map