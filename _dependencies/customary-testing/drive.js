export function input(text, element) {
    element.focus();
    element.value += text;
    element.dispatchEvent(new Event("input"));
}
export function textarea(text, element) {
    element.focus();
    element.value += text;
    element.dispatchEvent(new Event("input"));
}
export function checkbox(element) {
    element.focus();
    element.click();
    element.dispatchEvent(new Event("input"));
}
//# sourceMappingURL=drive.js.map