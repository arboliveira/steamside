import {WallpaperSettings} from "#steamside/application/modules/settings/WallpaperSettings";

export class WallpaperRender {
    static renderFirstWallpaper(wallpaperSettings: WallpaperSettings): void {
        function urlToUse(): string | undefined {
            const lines = wallpaperSettings.wallpapers?.split(/\r?\n/);
            const active: string[] | undefined =
                lines?.filter(s => s.length > 0 && !s.startsWith('#'));

            return active?.[0];
        }

        const url: string | undefined = wallpaperSettings.enable_wallpaper ? urlToUse(): undefined;

        const bodyStyle: CSSStyleDeclaration = document.body.style;

        if (url) {
            bodyStyle.backgroundImage = `url(${url})`;
        }
        else {
            bodyStyle.backgroundImage = '';
        }

        const size: string | undefined = (wallpaperSettings.enable_wallpaper && wallpaperSettings.preserve_aspect_ratio) ? 'contain' : undefined;

        bodyStyle.backgroundSize = size ? 'contain' : '';
    }
}
