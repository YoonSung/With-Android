package gaongil.safereturnhome.dto.cloud.client;


import android.content.Context;

import gaongil.safereturnhome.dto.UserDTO;
import gaongil.safereturnhome.dto.cloud.ClientStrategy;
import gaongil.safereturnhome.exception.ProcessFailureException;

/**
 * Created by yoon on 15. 7. 7..
 */
public class DialogForm implements ClientDTO {

    private String senderPhoneNumber;
    private String question;
    private String deny;
    private String confirm;
    private String comment;
    private String returnUrl;

    private DialogForm(){}

    public DialogForm(String question, String deny, String confirm, String comment, String returnUrl) {
        this.question = question;
        this.deny = deny;
        this.confirm = confirm;
        this.comment = comment;
        this.returnUrl = returnUrl;
    }

    public DialogForm(UserDTO sender, String question, String deny, String confirm, String comment, String returnUrl) {
        this.senderPhoneNumber = sender.getPhoneNumber();
        this.question = question;
        this.deny = deny;
        this.confirm = confirm;
        this.comment = comment;
        this.returnUrl = returnUrl;
    }

    public DialogForm(UserDTO sender, String question, String deny, String confirm, String returnUrl) {
        this.senderPhoneNumber = sender.getPhoneNumber();
        this.question = question;
        this.deny = deny;
        this.confirm = confirm;
        this.comment = comment;
        this.returnUrl = returnUrl;
    }


    @Override
    public void process(ClientStrategy strategy, Context context) throws ProcessFailureException {
        System.out.println("DialogForm");
    }
}
