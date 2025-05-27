export function q(target, options = {}) {
    const document = target.document;
    if (document)
        return document;
    const element = target;
    if (!options.shadowNot) {
        const shadowRoot = element.shadowRoot;
        if (shadowRoot)
            return shadowRoot;
    }
    return element;
}
export function querySelector(selectors, target, options = {}) {
    return q(target, options).querySelector(selectors)
        ?? (() => { throw new Error(`No element matching ${selectors}`); })();
}
export function querySelectorAll(selectors, target) {
    const list = q(target).querySelectorAll(selectors);
    return list.length > 0
        ? list
        : (() => { throw new Error(`No element matching ${selectors}`); })();
}
//# sourceMappingURL=query.js.map