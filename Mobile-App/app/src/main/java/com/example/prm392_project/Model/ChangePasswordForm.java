package com.example.prm392_project.Model;

public class ChangePasswordForm {
    private String curPass;
    private String newPass;
    private String confirmPass;

    public ChangePasswordForm() {
    }

    public ChangePasswordForm(String curPass, String newPass, String confirmPass) {
        this.curPass = curPass;
        this.newPass = newPass;
        this.confirmPass = confirmPass;
    }

    public String getCurPass() {
        return curPass;
    }

    public void setCurPass(String curPass) {
        this.curPass = curPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}
