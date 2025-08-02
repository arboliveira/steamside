export function kidsMode_from_url() {
    const kids_mode_param = new URLSearchParams(window.location.search)
        .get('kids');

    return !['false', 'no', '0', ''].includes(kids_mode_param?.toLowerCase() ?? '');
}
