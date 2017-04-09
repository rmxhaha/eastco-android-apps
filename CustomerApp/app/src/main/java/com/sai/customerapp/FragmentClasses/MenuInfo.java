package com.sai.customerapp.FragmentClasses;

public class MenuInfo {

    public int menuID;
    public String menuName;
    public String menuDescription;
    public int menuPrice;
    public String menuProfPic;

    public MenuInfo() {

    }

    public MenuInfo(int menuID, String menuName, String menuDescription, int menuPrice, String menuProfPic) {
        this.menuID = menuID;
        this.menuName = menuName;
        this.menuDescription = menuDescription;
        this.menuPrice = menuPrice;
        this.menuProfPic = menuProfPic;
    }

}