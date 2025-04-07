import {BackendDisabledError, BackendFetchError} from "#steamside/data-backend.js";

export function pop_toast(
	{error, target, offline_imagine_spot, completion_fn}: {
		error: Error,
		target: Element,
		offline_imagine_spot?: string,
		completion_fn?: Function,
	}
) {
	if (error instanceof BackendDisabledError) {
		const clarification = offline_imagine_spot
			? `Offline mode! Imagine ${offline_imagine_spot}... `
			: '';
		const requestInitString = error.requestInit
			? JSON.stringify(error.requestInit)
			: '';
		const toast = makeToast(
			`${clarification}${error.url}${requestInitString}`
		);
		toast.style.position = 'absolute';
		toast.style.inset = 'unset';
		const elementRect = target.getBoundingClientRect();
		toast.style.top = `${elementRect.bottom}px`;
		toast.style.left = `${elementRect.left}px`;
		document.body.appendChild(toast);
		toast.showPopover();
		setTimeout(
			() => {
				toast.hidePopover();
				document.body.removeChild(toast);
				completion_fn?.(target);
			},
			6000
		);
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

function makeToast(s: string) {
	const toast = document.createElement('article');
	toast.popover = 'manual';
	toast.innerText = s;
	return toast;
}
