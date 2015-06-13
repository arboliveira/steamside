import {Customary, CustomaryElement} from "#customary";
import {fetchSessionData, SessionData} from "#steamside/data-session.js";

import {CustomaryDeclaration} from "#customary";

export class PageFooterElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<PageFooterElement> =
		{
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
					'__sessionData': (el, a) => 
						el.__KidsModeIndicator_visible = a.kidsMode,
				},
			}
		}
	declare __KidsModeIndicator_visible: boolean;
	declare __sessionData: SessionData;

	async #on_connected()
	{
		this.__sessionData = await fetchSessionData();
	}
}
Customary.declare(PageFooterElement);
