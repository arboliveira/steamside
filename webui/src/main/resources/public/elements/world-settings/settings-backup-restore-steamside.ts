import {Customary, CustomaryDeclaration, CustomaryElement} from "#customary";

import {Skyward} from "#steamside/event-bus/Skyward.js";
import {toast} from "#steamside/vfx-toaster.js";

import {AllDataRead} from "#steamside/application/modules/backup/AllDataRead.js";
import {AllDataWrite} from "#steamside/application/modules/backup/AllDataWrite.js";

export class SettingsBackupRestoreElement extends CustomaryElement
{
	static customary: CustomaryDeclaration<SettingsBackupRestoreElement> =
		{
			name: 'settings-backup-restore-steamside',
			config: {
				construct: {shadowRootDont: true},
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
				},
				events: [
					{
						selector: "#BackupButton",
						listener: (el, e) => el.#onBackupClick(e),
					},
					{
						selector: "#RestoreButton",
						listener: (el, _e) => el.#onRestoreClick(),
					},
					{
						selector: "#RestoreFileInput",
						type: 'change',
						listener: (el, e) => el.#onRestoreFileInputClick(e),
					},
					{
						type: AllDataRead.eventTypeDone,
						listener: (el, e) =>
							el.#on_AllDataRead_DONE(<CustomEvent>e),
					},
					{
						type: AllDataWrite.eventTypeDone,
						listener: (el, _e) =>
							el.#on_AllDataWrite_DONE(),
					},
				]
			}
		}

	async #onBackupClick(e: Event)
	{
		Skyward.stage(e, this, {type: AllDataRead.eventTypePlease});
	}

	async #on_AllDataRead_DONE(event: CustomEvent<AllDataRead.DoneDetail>)
	{
		const data = event.detail.data;
		const backup = JSON.stringify(data, null, 2);
		const blob = new Blob([backup], {type: "application/json"});
		const fileURL = URL.createObjectURL(blob);

		const timestamp = new Date().toISOString().replace(/[:.]/g, '-');
		const suggestedName = `steamside-${timestamp}.json`;
		
		const downloadLink = document.createElement('a');
		downloadLink.style.display = 'none';
		downloadLink.href = fileURL;
		downloadLink.download = suggestedName;
		document.body.appendChild(downloadLink);
		downloadLink.click();
		document.body.removeChild(downloadLink);
		
		URL.revokeObjectURL(fileURL);
		
		toast({
			content: `🎯 Backup saved! 💾 ${suggestedName}`,
			target: event.target as HTMLElement,
		});
	}

	async #on_AllDataWrite_DONE() {
		window.location.reload();
	}

	async #onRestoreClick() {
		const restoreFileInput = this.renderRoot.querySelector<HTMLInputElement>("#RestoreFileInput")!;
		restoreFileInput.click();
	}

	async #onRestoreFileInputClick(e: Event) {
		const [file] = (<HTMLInputElement>e.target).files!;
		if (!file) {
			return;
		}
		const text = await file.text();
		const data = JSON.parse(text);

		Skyward.stage(e, this, {type: AllDataWrite.eventTypePlease, detail: {data}});
	}

	async #on_connected()
	{
	}
}
Customary.declare(SettingsBackupRestoreElement);
