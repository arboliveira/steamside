export function fromUrl(location: string): string {
	// same host and port
	return import.meta.resolve('./' + location);
	// FIXME autodetect a deep path and calibrate for root
}