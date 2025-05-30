import 'mocha';
import {expect} from "chai";
import * as CT from "#customary-testing";

import {BackendBridge} from "#steamside/application/BackendBridge.js";
import {WorldHomeElement} from "#steamside/elements-world-home-steamside.js";

const suite = {
    title: 'Kids World',
    subject_html: 'index.html?kids=true',
};

describe(suite.title, async function () {
    this.timeout(4000);
    this.slow(500);

    let _window: Window;
    before(() => _window = CT.open(suite.subject_html));
    after(() => _window.close());

    function findWorld() {
        return CT.querySelector('elements-world-home-steamside', _window);
    }

    function findHome() {
        const world = findWorld();
        return CT.querySelector('elements-world-home-kids-mode-steamside', world);
    }

    function findAtHome(segmentName: string) {
        const homeElement = findHome();
        return CT.querySelector(segmentName, homeElement);
    }

    function findDeck(container: Element) {
        return CT.querySelector('elements-game-card-deck-steamside', container);
    }

    function spotCard(needle: string, deck: Element): Element {
        return <Element>CT.spot(needle, deck, {selectors: 'elements-game-card-steamside'});
    }

    function findLoadingOverlay(card: Element) {
        return CT.querySelector('.game-tile-inner-loading-overlay', card);
    }

    const mockBackend = new BackendBridge({dryRun: true, forever: true});

    let _favoritesSegment: Element;
    let _favoritesDeck: Element;

    describe('happy day: Favorites', async function () {
        it('looks good', async function () {
            this.retries(64);

            const world = findWorld() as WorldHomeElement;
            world.sky.options.backend = mockBackend;

            _favoritesSegment = findAtHome('elements-world-home-favorites-segment-steamside');
        });
    });

    describe('happy day: Favorites: Play', async function () {
        const represent = 'Windosill';

        let game_link: HTMLElement;
        let loadingOverlay: Element;

        it('looks good', async function () {
            this.retries(128);
            _favoritesDeck = findDeck(_favoritesSegment);

            const card = spotCard(represent, _favoritesDeck);
            game_link = CT.querySelector('.game-link', card);
        });
        it('interact', async function () {
            // No asserts, just don't crash on mouseenter and mouseleave
            game_link.dispatchEvent(new MouseEvent('mouseenter', {bubbles: true}));
            game_link.dispatchEvent(new MouseEvent('mouseleave', {bubbles: true}));
            game_link.click();
        });
        it('looks good', async function () {
            this.retries(256);

            const card = spotCard(represent, _favoritesDeck);
            loadingOverlay = findLoadingOverlay(card);
            expect(loadingOverlay.checkVisibility());
        });
        it('unblock', async function () {
            mockBackend.quit();
        });
        it('looks good', async function () {
            this.retries(64);

            expect(loadingOverlay.checkVisibility()).false;
            
            const aUrl = mockBackend.getInputLastRequested();
            expect(aUrl).eq('api/app/37600/run');
        });
    });
});
