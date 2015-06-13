import { Customary, CustomaryElement } from "#customary";
import { CollectionPickerElement } from "#steamside/elements-collection-picker-steamside.js";
import { CollectionEditCombineCommandBoxElement } from "#steamside/elements-collection-edit-combine-command-box-steamside.js";
import { Backend } from "#steamside/data-backend.js";
import { pop_toast } from "#steamside/vfx-toaster.js";
export class CollectionEditCombineElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.backend = new Backend();
    }
    static { this.customary = {
        name: 'elements-collection-edit-combine-steamside',
        config: {
            define: {
                fontLocations: [
                    "https://fonts.googleapis.com/css?family=Arvo:regular,bold",
                    'https://fonts.googleapis.com/css?family=Karla:regular,bold'
                ],
            },
            state: [
                '__tag',
                'combine_command_box_visible',
                '__tag_combining_name',
            ],
        },
        hooks: {
            requires: [
                CollectionPickerElement,
                CollectionEditCombineCommandBoxElement,
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            changes: {
            //'__tag': (el, a) => el.#on_tag_change(a),
            },
            lifecycle: {
                connected: el => el.#on_connected(),
            },
            events: [
                {
                    type: 'CollectionPickerElement:CollectionPicked',
                    listener: (el, e) => el.#on_CollectionPickerElement_CollectionPicked(e),
                },
                {
                    type: 'CommandBoxElement:CommandPlease',
                    listener: (el) => el.#on_CommandBoxElement_CommandPlease(),
                },
                {
                    type: 'CommandBoxElement:CommandAlternatePlease',
                    listener: (el, event) => el.#on_CommandBoxElement_CommandAlternatePlease(event),
                },
                {
                    type: 'CommandBoxElement:ConfirmPlease',
                    listener: (el, event) => el.#on_CommandBoxElement_ConfirmPlease(event),
                },
            ],
        }
    }; }
    #on_CollectionPickerElement_CollectionPicked(e) {
        this.__tag_combining_name = e.detail;
        this.combine_command_box_visible = true;
    }
    #on_CommandBoxElement_CommandPlease() {
        // on command, all variations delete one or both supplier collections
        this.#askForConfirmation();
    }
    async #on_CommandBoxElement_CommandAlternatePlease(event) {
        // on command alternate, a fresh collection keeps both supplier collections
        const collection_editing_name = this.__tag.name;
        const collection_combining_name = this.__tag_combining_name;
        const inputValue = event.detail;
        const fresh = inputValue
            && inputValue !== collection_editing_name
            && inputValue !== collection_combining_name;
        if (!fresh) {
            this.#askForConfirmation();
            return;
        }
        await this.#combineAndKeepSources({ receiver: inputValue });
    }
    async #on_CommandBoxElement_ConfirmPlease(event) {
        const inputValue = event.detail;
        const DID_YOU_WRITE_TESTS_ALREADY = false;
        if (!DID_YOU_WRITE_TESTS_ALREADY) {
            throw new Error('Danger zone, write tests first');
        }
        // TODO read state: previous action was command or command alternate
        // TODO determine whether receiver is the editing or the combining
        await this.#combineAndDeleteSources({ receiver: inputValue });
    }
    #askForConfirmation() {
        const el = this.renderRoot
            .querySelector('elements-collection-edit-combine-command-box-steamside');
        el.showCommandConfirm();
    }
    async #combineAndKeepSources({ receiver }) {
        const collection_editing_name = this.__tag.name;
        const collection_combining_name = this.__tag_combining_name;
        const aUrl = "api/collection/" +
            encodeURIComponent(collection_editing_name) +
            "/combine/" +
            encodeURIComponent(collection_combining_name) +
            "/into/" +
            encodeURIComponent(receiver) +
            "/copy";
        // TODO display 'combining...'
        try {
            await this.backend.fetchBackend({ url: aUrl });
            // TODO Parent: listen, close combine segment, and refresh tag on display
            this.dispatchEvent(new CustomEvent('CollectionEditCombineElement:Combined', {
                detail: {
                    receiver,
                    collection_editing_name,
                    collection_combining_name,
                },
                composed: true,
                bubbles: true,
            }));
        }
        catch (error) {
            const imagine = `${collection_editing_name} and` +
                ` ${collection_combining_name} were copied into ${receiver}`;
            pop_toast({
                error: error,
                offline_imagine_spot: imagine,
                target: this.renderRoot.firstElementChild,
            });
        }
    }
    async #combineAndDeleteSources({ receiver }) {
        const collection_editing_name = this.__tag.name;
        const collection_combining_name = this.__tag_combining_name;
        const aUrl = "api/collection/" +
            encodeURIComponent(collection_editing_name) +
            "/combine/" +
            encodeURIComponent(collection_combining_name) +
            "/into/" +
            encodeURIComponent(receiver) +
            "/move";
        // TODO display 'combining...'
        try {
            await this.backend.fetchBackend({ url: aUrl });
            // TODO Parent: listen, close combine segment, and refresh tag on display
            this.dispatchEvent(new CustomEvent('CollectionEditCombineElement:Combined', {
                detail: {
                    receiver,
                    collection_editing_name,
                    collection_combining_name,
                },
                composed: true,
                bubbles: true,
            }));
        }
        catch (error) {
            const imagine = `${collection_editing_name} and` +
                ` ${collection_combining_name} were moved into ${receiver}`;
            pop_toast({
                error: error,
                offline_imagine_spot: imagine,
                target: this.renderRoot.firstElementChild,
            });
        }
        this.dispatchEvent(new CustomEvent('CollectionEditCombineElement:Combined', {
            detail: {
                receiver,
                collection_editing_name,
                collection_combining_name,
            },
            composed: true,
            bubbles: true,
        }));
    }
    async #on_connected() {
        await this.backend.fetchSessionDataAndDisableBackendIfOffline();
    }
}
Customary.declare(CollectionEditCombineElement);
//# sourceMappingURL=elements-collection-edit-combine-steamside.js.map