import { ns } from "#steamside/application/names/ns.js";
export var event;
(function (event) {
    function typePlease(name) {
        return `${type(name)}:PLEASE`;
    }
    event.typePlease = typePlease;
    function typeDone(name) {
        return `${type(name)}:DONE`;
    }
    event.typeDone = typeDone;
    function typeChanged(name) {
        return `${type(name)}:CHANGED`;
    }
    event.typeChanged = typeChanged;
    function type(name) {
        return `${ns}:${name}`;
    }
})(event || (event = {}));
//# sourceMappingURL=event.js.map