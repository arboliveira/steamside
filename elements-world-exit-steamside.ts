import {Customary, CustomaryElement} from "#customary";
import {Backend} from "#steamside/data-backend.js";
import {fetchSessionData, SessionData} from "#steamside/data-session.js";
import {pop_toast} from "#steamside/vfx-toaster.js";

import {CustomaryDeclaration} from "#customary";

export class WorldExitElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<WorldExitElement> =
		{
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
					'command_line_visible': (el, a) =>
						el.#on_command_line_visible_changed(a),
					'error_on_exit': (el, a) =>
						el.#on_changed_error_on_exit(a),
				},
				events: {
					'#TryAgain': (el, e) => el.#clickTryAgain(e),
				},
			}
		}

	declare command_line_visible: boolean;
	declare try_again_text: string;
		
	async #on_connected()
	{
		await this.backend.fetchSessionDataAndDisableBackendIfOffline();

		const session: SessionData = await fetchSessionData();

		this.command_line = session.executable;
		
		await this.#do_exit();
	}

	declare command_line: string;
	declare error_on_exit: Error;
	
	async #do_exit() {
		try 
		{
			await this.backend.fetchBackend({url: 'api/exit'});
		}
		catch (error)
		{
			this.error_on_exit = error as Error;
		}
	}

	#on_command_line_visible_changed(a: boolean)
	{
		if (a) {
			const input = this.renderRoot.querySelector('#CommandLine') as HTMLInputElement;
			input.focus();
			input.select();
		}
	}

	#on_changed_error_on_exit(a: Error)
	{
		pop_toast({
			error: a,
			offline_imagine_spot: 'Steam is now closed',
			target: this.renderRoot.querySelector('.game-over-banner')!,
		});
	}

	#clickTryAgain(e: Event)
	{
		e.preventDefault();
		this.command_line_visible = true;
		this.try_again_text = "Copy to your command line:";
	}

	backend = new Backend();
}
Customary.declare(WorldExitElement);
