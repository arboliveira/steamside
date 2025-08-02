import { Customary, CustomaryElement } from "#customary";
import { Sideshow } from "#steamside/vfx-sideshow.js";
import { WorldSettingsCloudElement } from "#steamside/elements/world-settings/elements-world-settings-cloud-steamside.js";
import { WorldSettingsKidsElement } from "#steamside/elements/world-settings/elements-world-settings-kids-steamside.js";
import { SettingsWallpapersElement } from "#steamside/elements/world-settings/settings-wallpapers-steamside.js";
import { SettingsBackupRestoreElement } from "#steamside/elements/world-settings/settings-backup-restore-steamside.js";
export class WorldSettingsElement extends CustomaryElement {
    static { this.customary = {
        name: 'elements-world-settings-steamside',
        config: {
            state: [
                'statusVisible', 'statusText',
                'anotherUserVisible',
                'buttonVisible', 'buttonText'
            ],
            construct: { shadowRootDont: true },
        },
        hooks: {
            requires: [
                SettingsBackupRestoreElement,
                SettingsWallpapersElement,
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
                    listener: (el, e) => el.#on_CustomaryEvent_lifecycle_firstUpdated(e),
                }]
        }
    }; }
    #on_CustomaryEvent_lifecycle_firstUpdated(event) {
        this.#sideshow.on_firstUpdated_Event(event);
    }
    #on_firstUpdated() {
        this.#sideshow.showtime(this);
        Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
    }
    #sideshow = new Sideshow();
}
Customary.declare(WorldSettingsElement);
//# sourceMappingURL=elements-world-settings-steamside.js.map