export class MockBackend {
	async fetch({url}:{url: string}): Promise<void> {
		this.played_url = url;
		while (!this.let_it_go) {
			await new Promise(resolve => setTimeout(resolve, 100));
		}
	}
	played_url: string = '';
	let_it_go: boolean = false;
}
