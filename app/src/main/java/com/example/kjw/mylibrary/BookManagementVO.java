package com.example.kjw.mylibrary;


public class BookManagementVO {
    private int position;
    private int flag;
    BookManagementVO(int pos,int fla){
        setPosition(pos);
        setFlag(fla);
    }
    public void setPosition(int index){
        position=index;
    }
    public void setFlag(int f){
        flag=f;
    }
    public int getPosition(){
        return position;
    }
    public int getFlag(){
        return flag;
    }
}
