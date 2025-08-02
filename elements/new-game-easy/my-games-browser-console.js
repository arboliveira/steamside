(function() {
    return [...document.querySelectorAll('a._22awlPiAoaZjQMqxJhp-KP')]
        .map(tag => {
            return {
                id: tag.getAttribute('href').split('/').pop(),
                name: tag.innerText,
            };
        });
})();
