export function open(url?: string | URL): Window {
    return globalThis.window.open(url)
        ?? (() => {throw new Error(`Maybe your browser is blocking pop-ups? ${url}`)})();
}
