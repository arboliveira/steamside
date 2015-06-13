export function fromUrl(location) {
    const offline = true;
    const url = offline
        ? import.meta.resolve('./' + location)
        : 'http://localhost:42424/' + location;
    return url;
}
//# sourceMappingURL=data-url.js.map