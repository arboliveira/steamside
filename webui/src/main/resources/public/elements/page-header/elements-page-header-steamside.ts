import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {Skyward} from "#steamside/event-bus/Skyward.js";

import {WallpaperSettingsRead} from "#steamside/application/modules/settings/WallpaperSettingsRead.js";
import {WallpaperRender} from "#steamside/application/modules/settings/WallpaperRender.js";
import {KidsModeRead} from "#steamside/application/modules/kids/KidsModeRead.js";
import {kidsMode_from_url} from "#steamside/application/modules/kids/kidsMode_from_url.js";
import {LogoElement} from "#steamside/elements/logo/logo-steamside.js";

export class PageHeaderElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<PageHeaderElement> =
		{
			name: 'elements-page-header-steamside',
			config: {
				state: [
					'__advancedMode_visible',
					'__kidsMode_visible', '__kidsMode',
				],
				construct: {shadowRootDont: true},
				define: {
					fontLocations: [
						'https://fonts.googleapis.com/css?family=Arvo:regular,bold',
						'https://fonts.googleapis.com/css?family=Jura:regular,bold',
					],
				},
			},
			hooks: {
				requires: [
					LogoElement,
				],
				externalLoader: {
					import_meta: import.meta,
				},
				lifecycle: {
					connected: el => el.#on_connected(),
				},
				changes: {
					'__kidsMode': (el, a) => el.#on_kidsMode_change(a),
				},
				events: [
					{
						type: KidsModeRead.eventTypeDone,
						listener: (el, e) =>
							el.#on_KidsModeReadPlease_DONE(<CustomEvent>e),
					},
					{
						type: WallpaperSettingsRead.eventTypeDone,
						listener: (el, e) =>
							el.#on_WallpaperSettingsRead_DONE(<CustomEvent>e),
					},
				],
			}
		}
	declare __advancedMode_visible: boolean;
	declare __kidsMode_visible: boolean;
	declare __kidsMode: boolean;

	#on_kidsMode_change(a: boolean) {
		if (a)
		{
			this.__advancedMode_visible = false;
			this.__kidsMode_visible = true;
		}
		else
		{
			this.__advancedMode_visible = true;
			this.__kidsMode_visible = false;
		}
	}

	#on_KidsModeReadPlease_DONE(
		event: CustomEvent<KidsModeRead.DoneDetail>
	) {
		this.__kidsMode = event.detail.kidsMode || kidsMode_from_url();
    }

	#on_WallpaperSettingsRead_DONE(
		event: CustomEvent<WallpaperSettingsRead.DoneDetail>
	) {
		const wallpaperSettings = event.detail.wallpaperSettings;
		if (!wallpaperSettings) return;
		WallpaperRender.renderFirstWallpaper(wallpaperSettings);
    }

	#on_connected()
	{
		Skyward.fly(this, {type: KidsModeRead.eventTypePlease});
		Skyward.fly(this, {type: WallpaperSettingsRead.eventTypePlease});
	}
}
Customary.declare(PageHeaderElement);
