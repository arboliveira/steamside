import {Customary, CustomaryElement} from "#customary";
import {CommandBoxElement} from "#steamside/elements-command-box-steamside.js";
import {
    CommandHintWithVerbAndSubjectElement
} from "#steamside/elements-command-hint-with-verb-and-subject-steamside.js";

import {CustomaryDeclaration} from "#customary";

export class CollectionEditCombineCommandBoxElement extends CustomaryElement
{
    static customary: CustomaryDeclaration<CollectionEditCombineCommandBoxElement> =
        {
            name: 'elements-collection-edit-combine-command-box-steamside',
            config: {
                attributes: [
                    'collection_editing_name', 'collection_combining_name',
                ],
              state: [
                  'fused_name',
                  'verbA', 'subjectA', 'verbB', 'subjectB',
              ],
            },
            hooks: {
                externalLoader: {
                    import_meta: import.meta,
                    css_dont: true,
                },
                requires: [CommandBoxElement, CommandHintWithVerbAndSubjectElement],
                changes: {
                    'collection_editing_name': (el) => el.#update_fused_name(),
                    'collection_combining_name': (el) => el.#update_fused_name(),
                },
                events: [
                    {
                        type: 'CommandBoxElement:InputValueChanged',
                        listener: (el, event) =>
                                el.#on_CommandBoxElement_InputValueChanged(<CustomEvent>event),
                    },
                ],
            }
        }
    declare collection_editing_name: string;
    declare collection_combining_name: string;
    declare fused_name: string;
    declare verbA: string;
    declare subjectA: string;
    declare verbB: string;
    declare subjectB: string;

    showCommandConfirm() {
        const commandBox: CommandBoxElement = this.renderRoot.querySelector('elements-command-box-steamside')!;
        commandBox.showCommandConfirm();
    }    

    #update_fused_name() {
        const fused_name = `${this.collection_editing_name} and ${this.collection_combining_name}`;
        this.fused_name = fused_name;
        const commandBox: CommandBoxElement = this.renderRoot.querySelector('elements-command-box-steamside')!;
        commandBox.set_input_text_command_box_value(fused_name);
        commandBox.focus_on_input();
    }

    #on_CommandBoxElement_InputValueChanged(event: CustomEvent) {
        this.#updateHints(event.detail);
    }
    
    #updateHints(input_value: string) {
        if (!input_value) {
            this.#updateHints_collapsing(this.collection_editing_name);
            return;
        }
        if (
            input_value === this.collection_editing_name 
            || input_value === this.collection_combining_name
        ) {
            this.#updateHints_collapsing(input_value);
            return;
        }
        
        this.verbA = 'Create';
        this.subjectA =
            `*${input_value}* + [RED]DELETE ${this.collection_editing_name}[/RED] + [RED]DELETE ${this.collection_combining_name}[/RED] (will ask for confirmation)`;
        this.verbB = 'Create';
        this.subjectB =
            `*${input_value}* + [GREEN]Keep ${this.collection_editing_name}[/GREEN] + [GREEN]Keep ${this.collection_combining_name}[/GREEN]`;
        
        // FIXME third button: confirm
    }
    
    #updateHints_collapsing(collection_targeting: string) {
        const same_as_editing = collection_targeting === this.collection_editing_name;
        const l_name =
            same_as_editing ? this.collection_editing_name : this.collection_combining_name;
        const r_name =
            same_as_editing ? this.collection_combining_name : this.collection_editing_name;

        this.verbA = 'Update';
        this.subjectA =
            `*${l_name}* + [RED]DELETE ${r_name}[/RED] (will ask for confirmation)`;
        this.verbB = 'Update';
        this.subjectB =
            `*${r_name}* + [RED]DELETE ${l_name}[/RED] (will ask for confirmation)`;
    }
}
Customary.declare(CollectionEditCombineCommandBoxElement);
