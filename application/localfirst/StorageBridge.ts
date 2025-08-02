export class StorageBridge {
    constructor(private readonly storage: Storage) {}
    getItem(key: string): string | null {return this.storage.getItem(key)}
    setItem(key: string, value: string): void {this.storage.setItem(key, value)}
}
