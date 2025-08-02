export class StorageBridge {
    constructor(storage) {
        this.storage = storage;
    }
    getItem(key) { return this.storage.getItem(key); }
    setItem(key, value) { this.storage.setItem(key, value); }
}
//# sourceMappingURL=StorageBridge.js.map