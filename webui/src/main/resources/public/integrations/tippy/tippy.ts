import "https://unpkg.com/@popperjs/core@2/dist/umd/popper.min.js";
import "https://unpkg.com/tippy.js@6/dist/tippy-bundle.umd.js";

importLightThemeStylesheet();

export function tippy(targets: any, props: any): HTMLElement {

    // @ts-ignore
    const _tippy: Function = globalThis['tippy'] as Function;
    return _tippy(targets, props);
}

function importLightThemeStylesheet() {
    const url = 'https://unpkg.com/tippy.js@6/themes/light.css';
    const link = document.createElement('link');
    link.rel = 'stylesheet';
    link.type = 'text/css';
    link.href = url;
    link.media = 'all';
    document.head.appendChild(link);
}
