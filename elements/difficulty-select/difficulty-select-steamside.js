import { Customary, CustomaryElement } from "#customary";
import { LogoElement } from "#steamside/elements/logo/logo-steamside.js";
export class DifficultySelectElement extends CustomaryElement {
    static { this.customary = {
        name: 'difficulty-select-steamside',
        config: {
            construct: {
                shadowRootDont: true,
            },
            define: {
                fontLocations: [
                    'https://fonts.googleapis.com/css?family=Jura:regular,bold'
                ],
            }
        },
        hooks: {
            requires: [
                LogoElement,
            ],
            externalLoader: {
                import_meta: import.meta,
            },
        }
    }; }
}
Customary.declare(DifficultySelectElement);
//# sourceMappingURL=difficulty-select-steamside.js.map