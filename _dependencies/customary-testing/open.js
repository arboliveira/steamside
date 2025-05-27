export function open(url) {
    return globalThis.window.open(url)
        ?? (() => { throw new Error(`Maybe your browser is blocking pop-ups? ${url}`); })();
}
//# sourceMappingURL=open.js.map