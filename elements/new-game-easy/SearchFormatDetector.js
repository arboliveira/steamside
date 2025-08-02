export class SearchFormatDetector {
    static detectSearchFormat(query) {
        const detectors = [
            (s) => SteamIdNameJson.detect(query),
            (s) => SteamIdCSV.detect(query),
            (s) => SteamUrlCSV.detect(query),
            (s) => SteamSearch.detect(query),
        ];
        const errors = [];
        function detect() {
            for (const detector of detectors) {
                try {
                    return detector(query);
                }
                catch (e) {
                    errors.push(e);
                }
            }
            return undefined;
        }
        const result = detect();
        return { result, errors };
    }
}
export class SteamIdNameJson {
    constructor(json) {
        this.json = json;
    }
    static detect(query) {
        const array = JSON.parse(query);
        return new SteamIdNameJson(array.map(({ id, name }) => ({ id, name })));
    }
}
export class SteamIdCSV {
    constructor(ids) {
        this.ids = ids;
    }
    static detect(query) {
        return new SteamIdCSV(query
            .split(',')
            .map(s => s.trim())
            .filter(s => !s.startsWith('#'))
            .filter(s => !!parseInt(s)));
    }
}
export class SteamUrlCSV {
    static detect(query) {
        throw new Error('Not a set of Steam URLs');
    }
}
export class SteamSearch {
    constructor(query) {
        this.query = query;
    }
    static detect(query) {
        return new SteamSearch(query);
    }
}
//# sourceMappingURL=SearchFormatDetector.js.map