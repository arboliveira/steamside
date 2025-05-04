export function fromUrl(location) {
    // same host and port
    return import.meta.resolve('./' + location);
    // FIXME autodetect a deep path and calibrate for root
}
//# sourceMappingURL=data-url.js.map