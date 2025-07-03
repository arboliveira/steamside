import {fetchSessionData, SessionData} from "#steamside/data-session.js";

export class SessionHandshake {
    on_connected() {
        this.asyncSessionData = fetchSessionData();
    }
    async getSessionData(): Promise<SessionData> {
        return await this.asyncSessionData ??
            (()=>{throw new Error("on_connected() never called?!")})();
    }
    private asyncSessionData: Promise<SessionData> | undefined;
}
