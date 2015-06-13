export type Tag = {
	name: string;
	count?: number;
	builtin?: boolean;
}

export function toTag(tag: any): Tag {
	return {
		name: tag.name,
		count: tag.count, // can be undefined
	};
}
