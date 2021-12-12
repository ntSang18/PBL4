package com.bkzalo.event;

import com.bkzalo.model.Model_User;

public interface EventMain {
	//Hien thi form loading khi dang nhap, dang ki
    public void showLoading(boolean show);
    //Hien thi form chinh, goi su kien "list_user" -> Hien thi listuser form menu_left
    public void initChat();
    
    public void selectUser(Model_User user);//Model_User_Account user

    public void updateUser(Model_User user);//Model_User_Account user
}
