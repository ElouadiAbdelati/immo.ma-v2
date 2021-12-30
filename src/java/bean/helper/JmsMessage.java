/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean.helper;

/**
 *
 * @author soulaimane
 */
public class JmsMessage {

    private String annonceurEmail;
    private String referenceAnnonce;
    private boolean status;
    private String message;

    public JmsMessage() {
    }

    public JmsMessage(String annonceurEmail, String referenceAnnonce, boolean status, String message) {
        this.annonceurEmail = annonceurEmail;
        this.referenceAnnonce = referenceAnnonce;
        this.status = status;
        this.message = message;
    }

    public String getAnnonceurEmail() {
        return annonceurEmail;
    }

    public void setAnnonceurEmail(String annonceurEmail) {
        this.annonceurEmail = annonceurEmail;
    }

    public String getReferenceAnnonce() {
        return referenceAnnonce;
    }

    public void setReferenceAnnonce(String referenceAnnonce) {
        this.referenceAnnonce = referenceAnnonce;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "JmsMessage{" + "annonceurEmail=" + annonceurEmail + ", referenceAnnonce=" + referenceAnnonce + ", status=" + status + ", message=" + message + '}';
    }

}
