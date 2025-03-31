import {Customary, CustomaryElement} from "#customary";
import {
    CommandHintWithVerbAndSubjectElement
} from "#steamside/elements-command-hint-with-verb-and-subject-steamside.js";

//import {CustomaryDeclaration} from "#customary";

export class CommandBoxElement extends CustomaryElement {
    /**
     * @type {CustomaryDeclaration<CommandBoxElement>}
     */
    static customary =
    {
        name: 'elements-command-box-steamside',
        config: {
            state: [
                'input_text_command_box_value',
                'command_confirm_visible',
                'command_confirm_what_text',
                'command_trouble_visible',
                'command_trouble_text',
                '__command_label_visible',
                '__command_button_strip_visible',
            ],
            attributes: [
                'command_label_text',
                'command_label_visible',
                'placeholder_text',
                'command_button_strip_visible',
            ],
        },
        values: {
            'input_text_command_box_value': '',
            'command_label_text': 'Command label',
            'command_confirm_visible': false,
            'command_confirm_what_text': 'Yes, do it already',
            'command_trouble_visible': false,
            'command_trouble_text': '(trouble)',
        },
        hooks: {
            requires: [
                CommandHintWithVerbAndSubjectElement,
            ],
            externalLoader: {
                import_meta: import.meta,
                css_dont: true,
            },
            changes: {
                'input_text_command_box_value': (el, a) => 
                    el.#on_changed_input_text_command_box_value(a),
            },
            events: [
                {
                    type: 'keydown',
                    selector: '#input-text-command-box',
                    listener: (el, e) => el.#event_keydown_input(e),
                },
                {
                    type: 'keyup',
                    selector: '#input-text-command-box',
                    listener: (el, e) => el.#event_keyup_input(e),
                },
                {
                    type: 'change',
                    selector: '#input-text-command-box',
                    listener: (el, e) => el.#event_change_input(e),
                },
                {
                    type: 'click',
                    selector: '#command-button',
                    listener: (el, e) => el.#doCommand(e),
                },
                {
                    type: 'click',
                    selector: '#command-button-alternate',
                    listener: (el, e) => el.#doCommandAlternate(e),
                },
                {
                    type: 'click',
                    selector: '#command-button-confirm',
                    listener: (el, e) => el.#doCommandConfirm(e),
                },
            ],
            lifecycle: {
                willUpdate: (el) => el.#on_willUpdate(),
                updated: (el) => el.#on_updated(),
            }
        }
    }

    #on_updated() {
        if (this.next_render_do_focus) {
            this.next_render_do_focus = false;
            let inputQueryEl = this.input_query_el();
            inputQueryEl.focus();
            inputQueryEl.select();
        }
    }

    #on_willUpdate() {
        // TODO possibly no usages anywhere - remove
        this.__command_label_visible = this.command_label_visible === 'true';
        
        // TODO no usages anywhere - remove
        this.__command_button_strip_visible = this.command_button_strip_visible !== 'false';
    }
    
    #on_changed_input_text_command_box_value(a) {
        this.dispatchEvent(
            new CustomEvent(
                'CommandBoxElement:InputValueChanged', 
                {
                    detail: this.#trim(this.input_text_command_box_value),
                    composed: true,
                }
            )
        );
    }

    #doCommand()
    {
        this.dispatchEvent(
            new CustomEvent(
                'CommandBoxElement:CommandPlease',
                {
                    detail: this.#trim(this.input_text_command_box_value),
                    composed: true,
                }
            )
        );
    }

    #doCommandAlternate()
    {
        this.dispatchEvent(
            new CustomEvent(
                'CommandBoxElement:CommandAlternatePlease',
                {
                    detail: this.#trim(this.input_text_command_box_value),
                    composed: true,
                }
            )
        );
    }
    
    #doCommandConfirm()
    {
        this.dispatchEvent(
            new CustomEvent(
                'CommandBoxElement:ConfirmPlease',
                {
                    detail: this.#trim(this.input_text_command_box_value),
                    composed: true,
                }
            )
        );
    }

    #trim(s) {
        return s?.trim() || '';
    }

    #event_keyup_input(e) {
        // TODO figure out why hints break if this event is removed
        e.preventDefault();
        this.input_text_command_box_value = e.target.value;
    }

    #event_change_input(e) {
        e.preventDefault();
        this.input_text_command_box_value = e.target.value;
    }

    #event_keydown_input(e) {
        if (!(e.keyCode === 13)) return;

        e.preventDefault();

        if (e.shiftKey)
        {
            this.#doCommandConfirm();
            return;
        }

        if (e.ctrlKey)
        {
            this.#doCommandAlternate();
            return;
        }

        this.#doCommand();
    }

    input_query_el() {
        return this.renderRoot.querySelector('.command-text-input');
    }

    set_input_text_command_box_value(value) 
    {
        // TODO broken, write test. value is set but input is not updated
        this.input_text_command_box_value = value;
    }

    showCommandConfirm() {
        this.command_confirm_visible = true;
    }
    
    input_query_val() {
        return this.input_text_command_box_value;
    }

    focus_on_input() {
        this.next_render_do_focus = true;
        this.requestUpdate();
    }

    emptyCommandHints() {
        this.renderRoot.querySelector("#command-hint").replaceChildren();
        this.renderRoot.querySelector("#command-hint-alternate").replaceChildren();
        this.emptyCommandHintConfirm();
    }

    appendCommandHint(elHint) {
        this.renderRoot.querySelector("#command-hint").append(elHint);
    }

    appendCommandHintAlternate(elHint) {
        this.renderRoot.querySelector("#command-hint-alternate").append(elHint);
    }

    showCommandHintConfirm(elHint) {
        const el = this.emptyCommandHintConfirm();
        el.append(elHint);
        this.command_confirm_visible = true;
    }

    hideCommandHintConfirm()
    {
        this.emptyCommandHintConfirm();
        this.command_confirm_visible = false;
    }

    emptyCommandHintConfirm()
    {
        this.command_confirm_what_text = '';
        return this.renderRoot.querySelector("#command-confirm-what");
    }

    hideCommandButtonStrip()
    {
        this.command_button_strip_visible = false;
    }

    label_text( v )
    {
        this.command_label_text = v;
        this.command_label_visible = true;
    }

    trouble(error) {
        this.command_trouble_text =
            error.responseJSON?.message
            ?? error.status + ' ' + error.statusText;
        this.command_trouble_visible = true;
    }
}
Customary.declare(CommandBoxElement);
