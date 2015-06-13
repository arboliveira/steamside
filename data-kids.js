import { fromUrl } from "#steamside/data-url.js";
export async function fetchKidsData() {
    const location = 'api/kids/kids.json';
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    return json.map((entry) => toKid(entry));
}
export async function fetchKidData(id) {
    const location = `api/kids/kid/${id}.json`;
    const response = await fetch(fromUrl(location));
    const json = await response.json();
    return toKid(json);
}
function toKid(json) {
    return {
        id: json.id,
        name: json.name,
        user: json.user,
        inventory: json.inventory,
    };
}
//# sourceMappingURL=data-kids.js.map