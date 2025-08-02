import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {Sideshow} from "#steamside/vfx-sideshow.js";
import {Skyward} from "#steamside/event-bus/Skyward.js";
import {WallpaperSettingsWrite} from "#steamside/application/modules/settings/WallpaperSettingsWrite.js";
import {WallpaperRender} from "#steamside/application/modules/settings/WallpaperRender.js";
import {WallpaperSettingsRead} from "#steamside/application/modules/settings/WallpaperSettingsRead.js";
import {WallpaperSettings} from "#steamside/application/modules/settings/WallpaperSettings";

export class SettingsWallpapersElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<SettingsWallpapersElement> =
		{
			name: 'settings-wallpapers-steamside',
			config: {
				attributes: ['wallpapers', 'enable_wallpaper', 'preserve_aspect_ratio'],
				define: {
					fontLocations: [
						"https://fonts.googleapis.com/css?family=Arvo:regular,bold",
						'https://fonts.googleapis.com/css?family=Karla:regular',
					],
				},
				construct: {shadowRootDont: true},
			},
			hooks: {
				externalLoader: {import_meta: import.meta, css_dont: true},
				lifecycle: {
					connected: el => el.#on_connected(),
					firstUpdated: el => el.#on_firstUpdated(),
					updated: el => el.#on_updated(),
				},
				changes: {
					'enable_wallpaper': (el, _a) => el.preview(),
					'wallpapers': (el, _a) => el.preview(),
					'preserve_aspect_ratio': (el, _a) => el.preview(),
				},
				events: [
					{
						type: WallpaperSettingsRead.eventTypeDone,
						listener: (el, e) =>
							el.#on_WallpapersRead_DONE(<CustomEvent>e),
					},
					{
						selector: "#SaveButton",
						listener: (el, e) => el.#onSaveClick(e),
					},
				]
			}
		}
	declare wallpapers: string;
	declare enable_wallpaper: string;
	declare preserve_aspect_ratio: string;

	declare focus_please: string | undefined;

	#on_firstUpdated() {
		Sideshow.customary_dispatchEvent_Customary_lifecycle_firstUpdated(this);
	}

	#on_updated() {
		if (this.focus_please === 'true') {
			this.focus_please = undefined;
			const focusWanted = 'input[data-customary-bind="wallpapers"]';
			const el = this.renderRoot.querySelector(focusWanted) as HTMLElement;
			el.focus();
		}
	}

	private preview() {
		WallpaperRender.renderFirstWallpaper(this.encodeWallpaperSettings());
	}

	private encodeWallpaperSettings(): WallpaperSettings {
		return {
			enable_wallpaper: this.enable_wallpaper === 'true',
			wallpapers: this.wallpapers,
			preserve_aspect_ratio: this.preserve_aspect_ratio === 'true',
		};
	}

	async #onSaveClick(e: Event) {
		Skyward.stage<WallpaperSettingsWrite.EventDetail>(
			e, this, {
				type: WallpaperSettingsWrite.eventTypePlease,
				detail: {
					wallpaperSettings: this.encodeWallpaperSettings(),
				},
			});
	}

	#on_WallpapersRead_DONE(
		event: CustomEvent<WallpaperSettingsRead.DoneDetail>
	) {
		const wallpaperSettings = event.detail.wallpaperSettings;
		this.enable_wallpaper = wallpaperSettings?.enable_wallpaper ? 'true' : 'false';
		this.wallpapers = wallpaperSettings?.wallpapers ?? '';
		this.preserve_aspect_ratio = wallpaperSettings?.preserve_aspect_ratio ? 'true' : 'false';
	}

	async #on_connected()
	{
		Skyward.fly(this, {type: WallpaperSettingsRead.eventTypePlease});
	}
}
Customary.declare(SettingsWallpapersElement);
