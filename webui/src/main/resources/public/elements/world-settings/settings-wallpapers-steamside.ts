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


const WALLPAPERS =
	`
https://community.fastly.steamstatic.com/economy/profilebackground/items/1195690/bf89f1cbb680f7d972eb856d51e33f988abad8b9.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1195690/ad57529d162951e8705c9c9307d572522062f21b.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1195690/0a7a2991cb288dad80414165bd2ac3c2a15f1c14.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1195690/0cd0af7597f43130e4a44c3358848621ec8cca0d.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1195690/e1f8b724e45a26a667738fa6aa4de93079992865.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/8f38c816fa71c64a234a67d4fcdcbe8bbc7cfae3.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/c8789f8b23006f35b049172308e343b5e9ec01be.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/69a0a6fd58d1add159dea2b4f146ce08822ecb57.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/6fad765e281a7194da6b47172a98d88b6509daf9.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/ea394a24ac81765048edb89316502a82b9f8ed3a.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/c61641cd40faa39a8339ea109177f078d0cfe338.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/70f1cc7cfc6d7df79c67bf6ba9849a5f21aa8f5c.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/1578531853e33dba12a1bdda2b0ae16a9519dd19.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/e622db439ba3e1a93c204fbcd7d05bc2312cf5ad.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/4ffed03a3e90ff1fff5089649787b3ea1317b0b2.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/f32f9ca07dcd11b9b76dc743cd3eaf6d6b398f29.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/df32f3aec08371a6dc1ba7e6b9f8d8aa57e96312.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/9786140231c9640ede914ff47412497290a52cce.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/bb2f2367e9ec63aa1d5061466a7d16b3e2d9f2c6.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/de11b810013108cb0ba9ff2262c1a3b7c452ee35.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/41c9fba5d718315a9fe016cb7d2ca43c025c9224.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/71a97c84ee3e02fdfa2c7e57aa2fae666be71085.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1095000/cf9182ee1d95dd0ca4f20937535f7053594519e8.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1083560/8ed0774c5f08546e3017571149424138e345622f.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1083560/4757c84b2c99c2c786e3a2c07585bbd3e21c371e.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1083560/2d007aa997029861169b730e82b4e174bed5b570.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1083560/6883b5e20e98484607f425971e16cd955d9f4a8b.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1017190/30bed7b8d8faefda0f1b86da79984e47e19a2aeb.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1017190/e09bd4353d8e00b5a3963f00ec912b1a875f1166.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1017190/575d19e7d5fabb313c6c4d99c5d37f15a80e0018.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/fb35c9934621215738372c5ae8689fe37ecd9d6d.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/ebd86b5efab614ff8829c26091ebe31edf4e6e4c.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/c24d6e49f4494d51aa189e8718a33a9795f09103.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/384f66e7923db5cab694b7f07a0e8db19fc90982.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/13ef0b53e0db35130f606a0cff40c1202706f3e7.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/e374553f6d91add989ecd08d5dcba62a8a41c94d.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/d10816961c9b2b5b29f7813de1d9340c7670f9bc.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/816f3949812b610d6ccef7c2fb3cf0758b2f6511.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/009f4370d61fd4777171a8f156f75e83f82f6999.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/d78ec8db442ecdc973f7adc4fb9348d082a9835c.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/93dad37385d814c53c5e5d70b18aed9013e08830.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/982a7054dfd93fb208335a62e9820d5440f0cd84.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/991980/8d1d6b0539973d1ec49ace0bcd67dc9f061b338b.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/866860/fd79d60f8aaa7f91c13b65e3d3a4ba00b8cecd61.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/866860/73acb86a7f41f06980f7f493fd320561e8704b67.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/866860/3171f482989a04022ee30d48116b77fca58b0e53.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/425280/e1ef6af3c50bc01f9e49b2b38f82c8d993daa9f0.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/368370/ba83984dfe8b6f36d76690aa70314cea68665c2c.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/425280/c5623c6a82fedb67387676d8701140c08742a6e9.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/220860/a2976f57fa596360daad497f6addaa87bc350b0d.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/232430/e3df58e7dab4daad07342aa274e09b21ff4dd8a4.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/248190/c218a1816079bdd4343c0b1f05b08bf1374c16d5.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/219150/e4c6d1863baf2f20dc06228d945cd7f739ed80b0.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/219740/d069b58e191d0218dca26785afd183714a790ab5.png
https://community.fastly.steamstatic.com/economy/profilebackground/items/303700/61472e686c4d6426dce0ae12264f85d15eef534e.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/27000/2605a0e86e1d2eeb79d8ccc6174d466311baa656.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/294140/d3f0f0deb227a041525be5eb9573289246bcdf01.JPG
https://community.fastly.steamstatic.com/economy/profilebackground/items/294140/148da5ea5667df237ce98eeddcf02734c806c7d5.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/267420/7c8fd709677577768ed892eb06acb9dee1846a5e.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/2021850/ad5177e5d02bfdaa43f094d3148d37c26fb706f7.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/70300/d9ba6c5a07ce0761540aee351998a41eabc31ae3.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/239030/d32f8014ebaee95bda4e25ffa9aadc0cd834c4d7.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/296870/d769295dd84c45c4091177b7e500fecf83ee50f1.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/212480/b34a6ba0442d2f2e4fe315a57de92c5037415b1c.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/278360/12c6cfd18c37780aa799b1a5b7827d18b582ee41.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1195670/fdc4c718b643db944258b610066827d3ec6d84c9.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1195670/79d8af678e121c2a46983bfd617dd92f1b518c05.jpg
https://community.fastly.steamstatic.com/economy/profilebackground/items/1195690/48573910a5610ad910a8fe624cf065bdf831571d.jpg
`.trimStart();

