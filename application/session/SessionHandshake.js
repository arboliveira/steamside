import { fetchSessionData } from "#steamside/data-session.js";
export class SessionHandshake {
    on_connected() {
        this.asyncSessionData = fetchSessionData();
    }
    async getSessionData() {
        return await this.asyncSessionData ??
            (() => { throw new Error("on_connected() never called?!"); })();
    }
}
//# sourceMappingURL=SessionHandshake.js.map