export function input(text: string, element: HTMLInputElement) {
    element.focus();
    element.value += text;
    element.dispatchEvent(new Event("input"));
}

export function textarea(text: string, element: HTMLTextAreaElement) {
    element.focus();
    element.value += text;
    element.dispatchEvent(new Event("input"));
}

export function checkbox(element: HTMLInputElement) {
    element.focus();
    element.click();
    element.dispatchEvent(new Event("input"));
}
