import { q, querySelectorAll } from "#customary-testing/query.js";
import { allTextContent } from "#customary-testing/read.js";
export function spot(needle, haystack, options = {}) {
    const { selectors } = options;
    const targets = selectors
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
function spot_(needle, node) {
    const textContent = allTextContent(node);
    if (textContent === needle) {
        return node;
    }
    for (const child of node.childNodes) {
        const found = spot_(needle, child);
        if (found)
            return found;
    }
    return undefined;
}
//# sourceMappingURL=spot.js.map