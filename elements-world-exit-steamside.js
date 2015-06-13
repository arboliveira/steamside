import { Customary, CustomaryElement } from "#customary";
import { Backend } from "#steamside/data-backend.js";
import { fetchSessionData } from "#steamside/data-session.js";
import { pop_toast } from "#steamside/vfx-toaster.js";
export class WorldExitElement extends CustomaryElement {
    constructor() {
        super(...arguments);
        this.backend = new Backend();
    }
    static { this.customary = {
        name: 'elements-world-exit-steamside',
        config: {
            state: [
                'try_again_text', 'command_line', 'command_line_visible',
                'error_on_exit',
            ],
            define: {
                fontLocations: [
                    'https://fonts.googleapis.com/css?family=Arvo:regular,bold',
                    'https://fonts.googleapis.com/css?family=Jura:regular,bold',
                ],
            },
        },
        values: {
            'try_again_text': 'Try again?',
        },
        hooks: {
            externalLoader: {
                import_meta: import.meta,
            },
            lifecycle: {
                connected: el => el.#on_connected(),
            },
            changes: {
                'command_line_visible': (el, a) => el.#on_command_line_visible_changed(a),
                'error_on_exit': (el, a) => el.#on_changed_error_on_exit(a),
            },
            events: {
                '#TryAgain': (el, e) => el.#clickTryAgain(e),
            },
        }
    }; }
    async #on_connected() {
        await this.backend.fetchSessionDataAndDisableBackendIfOffline();
        const session = await fetchSessionData();
        this.command_line = session.executable;
        await this.#do_exit();
    }
    async #do_exit() {
        try {
            await this.backend.fetchBackend({ url: 'api/exit' });
        }
        catch (error) {
            this.error_on_exit = error;
        }
    }
    #on_command_line_visible_changed(a) {
        if (a) {
            const input = this.renderRoot.querySelector('#CommandLine');
            input.focus();
            input.select();
        }
    }
    #on_changed_error_on_exit(a) {
        pop_toast({
            error: a,
            offline_imagine_spot: 'Steam is now closed',
            target: this.renderRoot.querySelector('.game-over-banner'),
        });
    }
    #clickTryAgain(e) {
        e.preventDefault();
        this.command_line_visible = true;
        this.try_again_text = "Copy to your command line:";
    }
}
Customary.declare(WorldExitElement);
//# sourceMappingURL=elements-world-exit-steamside.js.map