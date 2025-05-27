import 'mocha';
import * as sinon from 'sinon';
import * as CT from "#customary-testing";
import { SteamBrowserProtocolBackend, StatusBackend } from "#steamside/elements-world-steam-client-steamside.js";
const suite = {
    title: 'Steam Client World',
    subject_html: 'SteamClient.html',
};
describe(suite.title, async function () {
    this.timeout(4000);
    this.slow(500);
    let _window;
    before(() => _window = CT.open(suite.subject_html));
    after(() => _window.close());
    let world;
    let link_openSteamClient, link_downloads;
    const statusBackend = sinon.createStubInstance(StatusBackend);
    statusBackend.fetchStatus.resolves({ running: true, here: true });
    const steamBrowserProtocolBackend = sinon.createStubInstance(SteamBrowserProtocolBackend);
    describe('happy day', async function () {
        it('looks good', async function () {
            this.retries(64);
            world = CT.querySelector('elements-world-steam-client-steamside', _window);
            world.statusBackend = statusBackend;
            world.steamBrowserProtocolBackend = steamBrowserProtocolBackend;
            CT.spot('Steam is running.', world);
            link_openSteamClient = CT.spot('Open Steam Client', world);
        });
        it('interact', async function () {
            link_openSteamClient.click();
        });
        it('looks good', async function () {
            this.retries(512);
            sinon.assert.calledOnceWithExactly(steamBrowserProtocolBackend.sendCommand, 'api/steamclient/open/main');
            steamBrowserProtocolBackend.sendCommand.reset();
            link_downloads = CT.spot('Downloads', world);
        });
        it('interact', async function () {
            link_downloads.click();
        });
        it('looks good', async function () {
            this.retries(64);
            sinon.assert.calledOnceWithExactly(steamBrowserProtocolBackend.sendCommand, 'api/steamclient/open/downloads');
            steamBrowserProtocolBackend.sendCommand.reset();
        });
    });
});
//# sourceMappingURL=test-elements-world-steam-client-steamside-test.js.map