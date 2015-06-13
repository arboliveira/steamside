import { Customary, CustomaryElement } from "#customary";
import { fetchSessionData } from "#steamside/data-session.js";
export class PageFooterElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-page-footer-steamside',
        config: {
            state: [
                '__sessionData',
                '__KidsModeIndicator_visible',
            ],
            define: {
                fontLocations: [
                    'https://fonts.googleapis.com/css?family=Karla:regular,bold',
                ],
            },
        },
        hooks: {
            externalLoader: {
                import_meta: import.meta,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
            },
            changes: {
                '__sessionData': (el, a) => el.__KidsModeIndicator_visible = a.kidsMode,
            },
        }
    }; }
    async #on_connected() {
        this.__sessionData = await fetchSessionData();
    }
}
Customary.declare(PageFooterElement);
//# sourceMappingURL=elements-page-footer-steamside.js.map