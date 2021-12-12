package com.bkzalo.event;

import java.util.List;

import com.bkzalo.model.Model_Message;

public interface EventChat {
	//Chat_body.addItemRight
    public void sendMessage(Model_Message mgs);//Model_Send_Message data
    //Chat_body.addItemLeft
    public void receiveMessage(Model_Message mgs);//Model_Receive_Message data
    
    public void loadChat(List<Model_Message> l);
}
