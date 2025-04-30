export function tippy(targets: any, props: any): HTMLElement {
    // @ts-ignore
    const _tippy: Function = globalThis['tippy'] as Function;
    return _tippy(targets, props);
}
