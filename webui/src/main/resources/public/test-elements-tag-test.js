import 'mocha';
import { CustomaryTesting as CT } from "#customary-testing";
import { assert, expect } from "chai";

const suite = {
    title: 'Tag',
    subject_html: 'test-elements-tag-test.html',
};

describe(suite.title, async function () {
    this.timeout(4000);
    this.slow(500);
    let window;
    before(() => window = CT.open(suite.subject_html));
    after(() => window.close());
    describe('happy day', async function () {
        it('looks good', async function () {
            this.retries(64);

            const tagView = CT.querySelector('#TagAGameView', window);
            
            const tags = CT.querySelectorAll(".display-collection-name", tagView);
         
            function assertTag(n, expected) {
                const name = CT.querySelector(".tag-sticker-name", tags[n]);
                expect(CT.allTextContent(name)).to.equal(expected);
            }
            
            assertTag(0, 'Indie');
            assertTag(1, 'Hand drawn');
            assertTag(2, 'Unplayed');
            assertTag(3, 'Favorites');
            
            expect(tags).to.have.length(4);
        });
    });
});
