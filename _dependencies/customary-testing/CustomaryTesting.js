export class CustomaryTesting {
    static open(url) {
        return globalThis.window.open(url)
            ?? (() => { throw new Error(`Blocked?! ${url}`); })();
    }
    static spot(needle, haystack) {
        const parentNode = this.q(haystack);
        return this.spot_(needle, parentNode)
            ?? (() => { throw new Error(`No "${needle}" spotted`); })();
    }
    static spot_(needle, node) {
        const textContent = this.allTextContent(node);
        if (textContent === needle) {
            return node;
        }
        for (const child of node.childNodes) {
            const found = this.spot_(needle, child);
            if (found)
                return found;
        }
        return undefined;
    }
    static querySelector(selectors, target) {
        return CustomaryTesting.q(target).querySelector(selectors)
            ?? (() => { throw new Error(`No element matching ${selectors}`); })();
    }
    static querySelectorAll(selectors, target) {
        const list = CustomaryTesting.q(target).querySelectorAll(selectors);
        return list.length > 0
            ? list
            : (() => { throw new Error(`No element matching ${selectors}`); })();
    }
    static allTextContent(node) {
        const textContentArray = collectAllTextContent(node);
        return textContentArray
            .map(s => { const t = s.trim(); return t.length ? t : s; })
            .join('')
            .trim();
    }
    static input(text, input) {
        input.focus();
        input.value += text;
        input.dispatchEvent(new Event("input"));
    }
    static textarea(text, textarea) {
        textarea.focus();
        textarea.value += text;
        textarea.dispatchEvent(new Event("input"));
    }
    static checkbox(input) {
        input.focus();
        input.click();
        input.dispatchEvent(new Event("input"));
    }
    static q(target) {
        return target.document ?? target.shadowRoot ?? target;
    }
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
//# sourceMappingURL=CustomaryTesting.js.map