import 'mocha';
import 'sinon';
import { CustomaryTesting as CT } from "#customary-testing";
import Sinon from "sinon";
const suite = {
    title: 'Steam Client',
    subject_html: 'test-elements-world-steam-client-steamside-test.html',
};
describe(suite.title, async function () {
    this.timeout(4000);
    this.slow(500);
    let window;
    /**
     * @type {WorldSteamClientElement}
     */
    let element;
    let link_openSteamClient, link_downloads;
    let steamBrowserProtocolBackend;
    before(() => window = CT.open(suite.subject_html));
    after(() => window.close());
    describe('happy day', async function () {
        it('looks good', async function () {
            this.retries(64);
            element = CT.querySelector('elements-world-steam-client-steamside', window);
            steamBrowserProtocolBackend = element.steamBrowserProtocolBackend;
            CT.spot('Steam is running.', element);
            link_openSteamClient = CT.spot('Open Steam Client', element);
        });
        it('interact', async function () {
            link_openSteamClient.click();
        });
        it('looks good', async function () {
            this.retries(512);
            Sinon.assert.calledOnceWithExactly(steamBrowserProtocolBackend.sendCommand, 'api/steamclient/open/main');
            steamBrowserProtocolBackend.sendCommand.reset();
            link_downloads = CT.spot('Downloads', element);
        });
        it('interact', async function () {
            link_downloads.click();
        });
        it('looks good', async function () {
            this.retries(64);
            Sinon.assert.calledOnceWithExactly(steamBrowserProtocolBackend.sendCommand, 'api/steamclient/open/downloads');
            steamBrowserProtocolBackend.sendCommand.reset();
        });
    });
});
//# sourceMappingURL=test-elements-world-steam-client-steamside-test.js.map