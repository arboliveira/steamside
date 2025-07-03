import {ns} from "#steamside/application/names/ns.js";

export namespace event {
    export function typePlease(name: string): string {
        return `${type(name)}:PLEASE`;
    }
    export function typeDone(name: string): string {
        return `${type(name)}:DONE`;
    }
    function type(name: string): string {
        return `${ns}:${name}`;
    }
}
