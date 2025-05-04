import {CustomaryTesting} from "#customary-testing";

export class CustomaryTestingQueries {

    static matchesTextContent(element: Element, expected: string): boolean {
        return CustomaryTesting.allTextContent(element) === expected;
    }

    static findByTextContent(container: ParentNode, expected: string, {selector}:{selector: string}) {
        const elements = querySelectorAll(container, selector);
        for (const element of elements) {
            if (this.matchesTextContent(element, expected)) {
                return element;
            }
        }
        throw new Error(`No element matching ${selector} has textContent ${expected}`);
    }

    static findByClass(container: ParentNode, expected: string, {selector}:{selector: string}) {
        const elements = querySelectorAll(container, selector);
        for (const element of elements) {
            if (element.classList.contains(expected)) {
                return element;
            }
        }
        throw new Error(`No element matching ${selector} has class ${expected}`);
    }
}

function querySelectorAll(container: ParentNode, selector: string) {
    const elements = container.querySelectorAll(selector);
    if (elements.length === 0) throw new Error(`No element matches selector '${selector}'`);
    return elements;
}

