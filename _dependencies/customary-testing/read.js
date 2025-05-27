export function allTextContent(node) {
    const textContentArray = collectAllTextContent(node);
    return textContentArray
        .map(s => { const t = s.trim(); return t.length ? t : s; })
        .join('')
        .trim();
}
function collectAllTextContent(node) {
    if (node.nodeType === Node.COMMENT_NODE)
        return [];
    if (node.tagName === 'SCRIPT')
        return [];
    if (node.shadowRoot)
        return collectAllTextContent(node.shadowRoot);
    const textContent = node.textContent?.replace(/\s+/g, ' ');
    if (textContent?.trim?.().length)
        return [textContent];
    if (node.hasChildNodes())
        return Array.from(node.childNodes).flatMap(child => collectAllTextContent(child));
    if (textContent === ' ')
        return [textContent];
    return [];
}
//# sourceMappingURL=read.js.map