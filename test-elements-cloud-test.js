import 'mocha';
import * as CT from "#customary-testing";
import { expect } from "chai";
const suite = {
    title: 'Cloud',
    subject_html: 'test-elements-cloud-test.html',
};
describe(suite.title, async function () {
    this.timeout(4000);
    this.slow(500);
    let window;
    let viewBeingTested;
    before(() => window = CT.open(suite.subject_html));
    after(() => window.close());
    describe('happy day', async function () {
        it('looks good', async function () {
            this.retries(64);
            const randomSuggestion = "RANDOM_SUGGESTION";
            viewBeingTested = CT.querySelector('elements-world-settings-cloud-steamside', window);
            const input = CT.querySelector('input[data-customary-bind="cloudSyncedLocation"]', viewBeingTested);
            expect(input.value).to.equal(randomSuggestion);
        });
        it('interact', async function () {
            CT.querySelector("#SaveButton", viewBeingTested).click();
        });
        it('looks good', async function () {
            this.retries(64);
            // FIXME tooltip "Saved. You need to restart Steamside for changes to take effect!"
            // expect(CT.querySelectorAll(".tooltipster-content", window)).to.have.length(1);
        });
    });
});
//# sourceMappingURL=test-elements-cloud-test.js.map