package gaongil.safereturnhome.dto.cloud;

import gaongil.safereturnhome.model.User;

/**
 * Created by yoon on 15. 7. 7..
 */
public class DialogForm {

    private String senderPhoneNumber;
    private String question;
    private String deny;
    private String confirm;
    private String comment;
    private String returnUrl;

    public DialogForm(String question, String deny, String confirm, String comment, String returnUrl) {
        this.question = question;
        this.deny = deny;
        this.confirm = confirm;
        this.comment = comment;
        this.returnUrl = returnUrl;
    }

    public DialogForm(User sender, String question, String deny, String confirm, String comment, String returnUrl) {
        this.senderPhoneNumber = sender.getPhoneNumber();
        this.question = question;
        this.deny = deny;
        this.confirm = confirm;
        this.comment = comment;
        this.returnUrl = returnUrl;
    }

    public DialogForm(User sender, String question, String deny, String confirm, String returnUrl) {
        this.senderPhoneNumber = sender.getPhoneNumber();
        this.question = question;
        this.deny = deny;
        this.confirm = confirm;
        this.comment = comment;
        this.returnUrl = returnUrl;
    }
}
