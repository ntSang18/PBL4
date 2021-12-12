package com.bkzalo.form;

import com.bkzalo.form.Chat;
import com.bkzalo.form.Menu_Left;
import com.bkzalo.form.Menu_Right;
import com.bkzalo.model.Model_User;

import net.miginfocom.swing.MigLayout;

public class Home extends javax.swing.JLayeredPane {

    private Chat chat;

    public Home() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new MigLayout("fillx, filly", "0[200!]5[fill, 100%]5[200!]0", "0[fill]0"));
        this.add(new Menu_Left());
        chat = new Chat();
        this.add(chat);
        this.add(new Menu_Right());
        chat.setVisible(false);
    }

    public void setUser(Model_User user) { //Model_User_Account user
        chat.setUser(user);
        chat.setVisible(true);
    }

    public void updateUser(Model_User user) { //Model_User_Account user
        chat.updateUser(user);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1007, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 551, Short.MAX_VALUE)
        );
    }
}
