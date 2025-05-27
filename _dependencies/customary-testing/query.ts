export function q(
    target: Window | Element,
    options: {
        shadowNot?: boolean,
    } = {}
): ParentNode {
    const document = (target as Window).document;
    if (document) return document;
    const element = target as Element;
    if (!options.shadowNot) {
        const shadowRoot = element.shadowRoot;
        if (shadowRoot) return shadowRoot;
    }
    return element;
}

export function querySelector<T extends Element>(
    selectors: string, target: Window | Element,
    options: {
        shadowNot?: boolean,
    } = {}
): T {
    return q(target, options).querySelector(selectors)
        ?? (()=>{throw new Error(`No element matching ${selectors}`)})();
}

export function querySelectorAll<T extends Element>(
    selectors: string, target: Window | Element
): NodeListOf<T> {
    const list = q(target).querySelectorAll(selectors);
    return list.length > 0
        ? list as NodeListOf<T>
        : (()=>{throw new Error(`No element matching ${selectors}`)})();
}
