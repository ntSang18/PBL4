package com.bkzalo.event;

import java.util.List;

import com.bkzalo.model.Model_User;

public interface EventMenuLeft {
	//Hien thi list User
    public void listUser(List<Model_User> users);//List<Model_User_Account> users
    //Cap nhat status user khi user connect
    public void userConnect(int userId); //int userID
    //Cap nhat status user khi user disconnect
    public void userDisconnect(int userId);//int userID
}
