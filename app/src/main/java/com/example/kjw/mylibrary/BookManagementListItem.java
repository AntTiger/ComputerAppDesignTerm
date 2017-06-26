package com.example.kjw.mylibrary;


public class BookManagementListItem {
    private String titleStr;
    private String authorStr;
    private String callnumberStr;

    BookManagementListItem(String title,String author,String callnumber){
        setTitleStr(title);
        setCallnumberStr(callnumber);
        setAuthorStr(author);
    }
    public void setTitleStr(String text){
        titleStr=text;
    }
    public String getTitleStr(){
        return this.titleStr;
    }
    public void setAuthorStr(String text){
        authorStr=text;
    }
    public String getAuthorStr(){
        return this.authorStr;
    }
    public void setCallnumberStr(String text){
        callnumberStr=text;
    }
    public String getCallnumberStr(){
        return this.callnumberStr;
    }
}
