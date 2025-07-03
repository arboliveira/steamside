import {CustomaryDeclaration} from "#customary";

import {SteamsideApplication} from "#steamside/application/SteamsideApplication.js";
import {PageHeaderElement} from "#steamside/elements/page-header/elements-page-header-steamside.js";
import {PageFooterElement} from "#steamside/elements-page-footer-steamside.js";

export function pageDeclaration(clazz: typeof HTMLElement): CustomaryDeclaration<HTMLElement> {
    return {
        config: {construct: {shadowRootDont: true}},
        hooks: {
            requires: [clazz, PageHeaderElement, PageFooterElement],
            lifecycle: {
                connected: el =>
                    ((el as any).app = new SteamsideApplication({eventBus: {switchboard: el}})).on_connected(),
                disconnected: el =>
                    (<SteamsideApplication>(el as any).app).on_disconnected(),
            }
        }
    };
}