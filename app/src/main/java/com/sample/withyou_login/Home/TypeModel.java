package com.sample.withyou_login.Home;

public class TypeModel {

    Integer langLogo;
    String langNAme;

    public TypeModel(Integer langLogo, String langNAme) {
        this.langLogo = langLogo;
        this.langNAme = langNAme;
    }

    public Integer getLangLogo() {
        return langLogo;
    }

    public void setLangLogo(Integer langLogo) {
        this.langLogo = langLogo;
    }

    public String getLangNAme() {
        return langNAme;
    }

    public void setLangNAme(String langNAme) {
        this.langNAme = langNAme;
    }
}
