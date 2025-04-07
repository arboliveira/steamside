import {Customary, CustomaryElement} from "#customary";
import {CommandBoxElement} from "#steamside/elements-command-box-steamside.js";
import {
    CommandHintWithVerbAndSubjectElement
} from "#steamside/elements-command-hint-with-verb-and-subject-steamside.js";

import {CustomaryDeclaration} from "#customary";

export class WorldHomeSearchCommandBoxElement extends CustomaryElement
{
    static customary: CustomaryDeclaration<WorldHomeSearchCommandBoxElement> =
        {
            config: {
              attributes: ['last_played_name_1', 'last_played_name_2'],
              state: ['command_box_entered', 'verbA', 'subjectA', 'verbB', 'subjectB'],
            },
            name: 'elements-world-home-search-command-box-steamside',
            hooks: {
                externalLoader: {
                    import_meta: import.meta,
                    css_dont: true,
                },
                requires: [CommandBoxElement, CommandHintWithVerbAndSubjectElement],
                lifecycle: {
                  willUpdate: el => el.#on_willUpdate(),
                },
                events: [
                    {
                        type: 'CommandBoxElement:InputValueChanged',
                        listener: (el, event) =>
                                el.#on_changed_input_text_command_box_value(<CustomEvent<any>>event),
                    },
                ],
            }
        }

    declare command_box_entered: string;
    declare verbA: string;
    declare subjectA: string;
    declare verbB: string;
    declare subjectB: string;
    declare last_played_name_1: string;
    declare last_played_name_2: string;

    #on_changed_input_text_command_box_value(event: CustomEvent) {
        this.command_box_entered = event.detail;
        // FIXME Third command button: Windosill community hub
    }
    
    #on_willUpdate() {
        if (this.command_box_entered) {
            this.verbA = 'search';
            this.subjectA = this.command_box_entered;
            this.verbB = 'play first result for';
            this.subjectB = this.command_box_entered;
        } else {
            this.verbA = 'continue';
            this.subjectA = this.last_played_name_1;
            // FIXME only one continue? hide alternate
            this.verbB = 'continue';
            this.subjectB = this.last_played_name_2;
        }
        // FIXME no continues? exploreACommandModel, exploreBCommandModel
    }
}
Customary.declare(WorldHomeSearchCommandBoxElement);
