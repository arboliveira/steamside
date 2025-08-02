import { Customary, CustomaryElement } from "#customary";
import { Backend } from "#steamside/data-backend.js";
import { pop_toast } from "#steamside/vfx-toaster.js";
import { CommandBoxElement } from "#steamside/elements-command-box-steamside.js";
import { fetchCloudSettingsData } from "#steamside/data-cloud-settings.js";
import { Sideshow } from "#steamside/vfx-sideshow.js";
import { SegmentElement } from "#steamside/elements/segment/segment-steamside.js";
export class WorldSettingsCloudElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.backend = new Backend();
    }
    static { this.customary = {
        name: 'elements-world-settings-cloud-steamside',
        config: {
            attributes: [
                'cloudSyncedLocation',
                'focus_please',
            ],
            state: [
                '__cloudSettings',
            ],
            define: {
                fontLocations: [
                    "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
                    'https://fonts.googleapis.com/css?family=Karla:regular',
                ],
            },
            construct: { shadowRootDont: true },
        },
        hooks: {
            requires: [
                SegmentElement, CommandBoxElement,
            ],
            properties: {
                '__cloudEnabled': { type: Boolean },
            },
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
                firstUpdated: el => el.#on_firstUpdated(),
                updated: el => el.#on_updated(),
            },
            changes: {
                '__cloudSettings': (el, a) => el.#on_cloudSettings_change(a),
            },
            events: [
                {
                    selector: "#SaveButton",
                    listener: (el, e) => el.#editSave(e),
                },
                {
                    selector: "input[type='checkbox']",
                    type: 'change',
                    listener: el => el.__cloudEnabled = !el.__cloudEnabled,
                },
            ],
        }
    }; }
    #on_updated() {
        if (this.focus_please === 'true') {
            this.focus_please = undefined;
            const focusWanted = 'input[data-customary-bind="cloudSyncedLocation"]';
            const el = this.renderRoot.querySelector(focusWanted);
            el.focus();
        }
    }
    #on_cloudSettings_change(a) {
        this.__cloudEnabled = a.cloudEnabled;
        this.cloudSyncedLocation = a.cloudSyncedLocation;
    }
    async #editSave(e) {
        const toastAnchor = e.currentTarget;
        const payloadPOST = {
            cloud: this.__cloudEnabled,
            cloudSyncedLocation: this.cloudSyncedLocation,
        };
        // FIXME popover-tooltip "Saving..."
        try {
            await this.backend.fetchBackend({
                url: "api/cloud/cloud.json",
                requestInit: {
                    method: 'POST',
                    body: JSON.stringify(payloadPOST),
                },
            });
            // FIXME popover-tooltip "Saved." (see SaveButton.title for full text)
        }
        catch (error) {
            pop_toast({
                error: error,
                offline_imagine_spot: `Cloud settings were saved`,
                target: toastAnchor,
            });
        }
    }
    #on_firstUpdated() {
        Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
    }
    async #on_connected() {
        await this.backend.fetchSessionDataAndDisableBackendIfOffline();
        if (!this.__cloudSettings) {
            this.__cloudSettings = await fetchCloudSettingsData();
        }
    }
}
Customary.declare(WorldSettingsCloudElement);
//# sourceMappingURL=elements-world-settings-cloud-steamside.js.map