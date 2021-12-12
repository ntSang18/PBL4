 package com.bkzalo.form;

import java.util.List;

import com.bkzalo.component.Chat_Body;
import com.bkzalo.component.Chat_Bottom;
import com.bkzalo.component.Chat_Title;
import com.bkzalo.event.EventChat;
import com.bkzalo.event.PublicEvent;
import com.bkzalo.model.Model_Message;
import com.bkzalo.model.Model_User;

import net.miginfocom.swing.MigLayout;

public class Chat extends javax.swing.JPanel {

    private Chat_Title chatTitle;
    private Chat_Body chatBody;
    private Chat_Bottom chatBottom;

    public Chat() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx", "0[fill]0", "0[]0[100%, fill]0[shrink 0]0"));
        
        chatTitle = new Chat_Title();
        chatBody = new Chat_Body();
        chatBottom = new Chat_Bottom();
        //Event Chat 
        PublicEvent.getInstance().addEventChat(new EventChat() {
            @Override
            public void sendMessage(Model_Message mgs) {
                chatBody.addItemRight(mgs);
            }

            @Override
            public void receiveMessage(Model_Message mgs) {
                if (chatTitle.getUser().getId() == mgs.getIdUserSend()) {
                    chatBody.addItemLeft(mgs);
                }
            }

			@Override
			public void loadChat(List<Model_Message> l) {
				for (Model_Message mgs : l) {
					if(chatTitle.getUser().getId() == mgs.getIdUserSend()) {
						chatBody.addItemLeft(mgs);
					}
					else
					{
						chatBody.addItemRight(mgs);
					}
				}
			}
        });
        add(chatTitle, "wrap");
        add(chatBody, "wrap");
        add(chatBottom, "h ::50%");
    }

    public void setUser(Model_User user) { //Model_User_Account user
        chatTitle.setUserName(user);
        chatBottom.setUser(user);
        chatBody.clearChat();
    }

    //Set status user
    public void updateUser(Model_User user) { //Model_User_Account user
        chatTitle.updateUser(user);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 727, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 681, Short.MAX_VALUE)
        );
    }
}
