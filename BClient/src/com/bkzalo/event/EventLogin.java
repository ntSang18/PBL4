package com.bkzalo.event;

import com.bkzalo.model.Model_Account;

public interface EventLogin {

    public void login(Model_Account data);//Model_Login data

    public void register(Model_Account data, EventErorMessage error);

    public void goRegister();

    public void goLogin();
}
