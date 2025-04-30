import {Customary, CustomaryElement} from "#customary";
import {Sideshow} from "#steamside/vfx-sideshow.js";
import {WorldSettingsCloudElement} from "#steamside/elements-world-settings-cloud-steamside.js";
import {WorldSettingsKidsElement} from "#steamside/elements-world-settings-kids-steamside.js";

import {CustomaryDeclaration} from "#customary";

export class WorldSettingsElement extends CustomaryElement
{
    static customary: CustomaryDeclaration<WorldSettingsElement> =
    {
        name: 'elements-world-settings-steamside',
        config: {
            state: [
                'statusVisible', 'statusText', 
                'anotherUserVisible', 
                'buttonVisible', 'buttonText'
            ],
        },
        hooks: {
            requires: [
                WorldSettingsCloudElement,
                WorldSettingsKidsElement,
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                firstUpdated: el => el.#on_firstUpdated(),
            },
            events: [{
                type: 'CustomaryEvent:lifecycle:firstUpdated',
                listener: (el, e) =>
                    el.#on_CustomaryEvent_lifecycle_firstUpdated(e as CustomEvent),
            }]
        }
    }

    #on_CustomaryEvent_lifecycle_firstUpdated(event: CustomEvent) {
        this.#sideshow.on_firstUpdated_Event(event);
    }
    
    #on_firstUpdated() 
    {
        this.#sideshow.showtime(this as any);
        Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
    }

    #sideshow = new Sideshow();
}
Customary.declare(WorldSettingsElement);
