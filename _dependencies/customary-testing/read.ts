export function allTextContent(node: Node) {
    const textContentArray: string[] = collectAllTextContent(node);
    return textContentArray
        .map(s => {const t = s.trim(); return t.length ? t : s})
        .join('')
        .trim();
}

function collectAllTextContent(node: Node): string[] {
    if (node.nodeType === Node.COMMENT_NODE) return [];
    if ((node as Element).tagName === 'SCRIPT') return [];
    if ((node as Element).shadowRoot) return collectAllTextContent((node as Element).shadowRoot!);
    const textContent = node.textContent?.replace(/\s+/g, ' ');
    if (textContent?.trim?.().length) return [textContent];
    if (node.hasChildNodes()) return Array.from(node.childNodes).flatMap(child => collectAllTextContent(child));
    if (textContent === ' ') return [textContent];
    return [];
}
