import { Customary, CustomaryElement } from "#customary";
export class LogoElement extends CustomaryElement {
    static { this.customary = {
        name: 'logo-steamside',
        config: {
            attributes: ['size'],
            state: ['size_class'],
            construct: {
                shadowRootDont: true,
            },
            define: {
                fontLocations: [
                    'https://fonts.googleapis.com/css?family=Arvo:regular,bold',
                    'https://fonts.googleapis.com/css?family=Jura:regular,bold'
                ],
            }
        },
        hooks: {
            externalLoader: {
                import_meta: import.meta,
            },
            changes: {
                'size': (el) => {
                    el.size_class = el.size === 'small' ? 'logo-steamside-small' : 'logo-steamside-large';
                    /*
                    <div class="enamel-plate ${classMap(this.classMapDirective_classes)}">
                     */
                    el.classMapDirective_classes = {
                        'logo-steamside-small': el.size === 'small',
                        'logo-steamside-large': el.size === 'large',
                    };
                },
            },
        }
    }; }
}
Customary.declare(LogoElement);
//# sourceMappingURL=logo-steamside.js.map