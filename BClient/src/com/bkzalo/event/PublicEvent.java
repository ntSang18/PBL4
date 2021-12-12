package com.bkzalo.event;

import com.bkzalo.event.EventChat;
import com.bkzalo.event.EventImageView;
import com.bkzalo.event.EventLogin;
import com.bkzalo.event.EventMain;
import com.bkzalo.event.EventMenuLeft;
import com.bkzalo.event.PublicEvent;

public class PublicEvent {

    private static PublicEvent instance;
    //
    private EventMain eventMain;
    //View Image
    private EventImageView eventImageView;
    private EventChat eventChat;
    //Gom cac su kien login, register, chuyen form login, chuyen form register
    private EventLogin eventLogin;
    //Gom cac su kien them user, cap nhat trang thai user
    private EventMenuLeft eventMenuLeft;

    public static PublicEvent getInstance() {
        if (instance == null) {
            instance = new PublicEvent();
        }
        return instance;
    }

    private PublicEvent() {

    }

    public void addEventMain(EventMain event) {
        this.eventMain = event;
    }

    public void addEventImageView(EventImageView event) {
        this.eventImageView = event;
    }

    public void addEventChat(EventChat event) {
        this.eventChat = event;
    }

    public void addEventLogin(EventLogin event) {
        this.eventLogin = event;
    }

    public void addEventMenuLeft(EventMenuLeft event) {
        this.eventMenuLeft = event;
    }

    public EventMain getEventMain() {
        return eventMain;
    }

    public EventImageView getEventImageView() {
        return eventImageView;
    }

    public EventChat getEventChat() {
        return eventChat;
    }

    public EventLogin getEventLogin() {
        return eventLogin;
    }

    public EventMenuLeft getEventMenuLeft() {
        return eventMenuLeft;
    }
}
