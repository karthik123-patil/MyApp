package com.test.util;

public class ItemTitleDAO {
    private String strTitleName, strTitle, strSource;

    public ItemTitleDAO(String strTitleName, String strTitle, String strSource){
        this.strTitleName  = strTitleName;
        this.strTitle = strTitle;
        this.strSource = strSource;
    }

    public String getStrTitleName() {
        return strTitleName;
    }

    public void setStrTitleName(String strTitleName) {
        this.strTitleName = strTitleName;
    }

    public String getStrTitle() {
        return strTitle;
    }

    public void setStrTitle(String strTitle) {
        this.strTitle = strTitle;
    }

    public String getStrSource() {
        return strSource;
    }

    public void setStrSource(String strSource) {
        this.strSource = strSource;
    }
}
