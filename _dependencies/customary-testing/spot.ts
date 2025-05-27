import {q, querySelectorAll} from "#customary-testing/query.js";
import {allTextContent} from "#customary-testing/read.js";

export function spot(
    needle: string,
    haystack: Window | Element,
    options: {
        selectors?: string
    } = {}
): Node {
    const {selectors} = options;
    const targets: Array<Window | Element> =
        selectors
            ? Array.from(querySelectorAll(selectors, haystack))
            : [haystack];

    for (const target of targets) {
        const found = spot_(needle, q(target));
        if (found) {
            return found;
        }
    }

    const inAny = selectors ? ` in any ${selectors}` : '';
    throw new Error(`No "${needle}" spotted${inAny}`);
}

function spot_(
    needle: string,
    node: Node
): Node | undefined {
    const textContent = allTextContent(node);
    if (textContent === needle) {
        return node;
    }
    for (const child of node.childNodes) {
        const found = spot_(needle, child);
        if (found) return found;
    }
    return undefined;
}
