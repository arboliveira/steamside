export function toTag(tag) {
	return {
		name: tag.name,
		count: tag.count, // can be undefined
	};
}
