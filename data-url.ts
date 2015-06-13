export function fromUrl(location: string): string {
	const offline = true;
	const url = offline
		? import.meta.resolve('./'+location)
		: 'http://localhost:42424/' + location;
	return url;
}