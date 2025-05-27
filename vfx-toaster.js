import { BackendDisabledError, BackendFetchError } from "#steamside/data-backend.js";
import { tippy } from "#steamside/integrations/tippy/tippy.js";
export function toastOrNot({ content, target }) {
    return content ? toast({ content, target }) : undefined;
}
export function toast({ content, target }) {
    return tippy(target, {
        content,
        //hideOnClick: false,
        maxWidth: 'none',
        placement: 'bottom',
        showOnCreate: true,
        theme: 'light',
        trigger: 'manual',
    });
}
export function toastError({ content, target }) {
    return tippy(target, {
        content,
        //hideOnClick: false,
        maxWidth: 'none',
        placement: 'bottom',
        showOnCreate: true,
        theme: 'error',
        trigger: 'manual',
    });
}
export function pop_toast({ error, target, offline_imagine_spot, completion_fn }) {
    if (error instanceof BackendDisabledError) {
        const clarification = offline_imagine_spot
            ? `Offline mode! Imagine ${offline_imagine_spot}... `
            : '';
        const requestInitString = error.requestInit
            ? JSON.stringify(error.requestInit)
            : '';
        const toast = makeToast(`${clarification}${error.url}${requestInitString}`);
        toast.style.position = 'absolute';
        toast.style.inset = 'unset';
        const elementRect = target.getBoundingClientRect();
        toast.style.top = `${elementRect.bottom}px`;
        toast.style.left = `${elementRect.left}px`;
        document.body.appendChild(toast);
        toast.showPopover();
        setTimeout(() => {
            end_toast(toast);
            completion_fn?.(target);
        }, 6000);
        return;
    }
    const s = error instanceof BackendFetchError
        ? `${error.response.status} ${error.response.statusText} ${error.response.url}`
        : error.message;
    const toast = makeToast(s);
    document.body.appendChild(toast);
    toast.showPopover();
    completion_fn?.(target);
    throw error;
}
function makeToast(s) {
    const toast = document.createElement('article');
    toast.popover = 'manual';
    toast.innerText = s;
    return toast;
}
export function end_toast(toast) {
    toast.hidePopover();
    document.body.removeChild(toast);
}
//# sourceMappingURL=vfx-toaster.js.map