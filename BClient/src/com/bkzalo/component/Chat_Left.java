package com.bkzalo.component;

import java.awt.Color;
import java.io.File;
import java.time.LocalTime;
import java.util.Date;

import javax.swing.Icon;


public class Chat_Left extends javax.swing.JLayeredPane {
	
    private com.bkzalo.component.Chat_Item txt;

    public Chat_Left() {
        initComponents();
        txt.setBackground(new Color(242, 242, 242));
    }

    public void setText(String text) {
        if (text.equals("")) {
            txt.hideText();
        } else {
            txt.setText(text);
        }

    }

    public void setImage(File file) {
        txt.setImage(false, file);
    }

    public void setFile(String fileName, String fileSize) {
        txt.setFile(false, fileName, fileSize);
    }

    public void setEmoji(Icon icon) {
        txt.hideText();
        txt.setEmoji(false, icon);
    }

    public void setTime() {
    	LocalTime time = LocalTime.now();
        txt.setTime(time.getHour() + ":" + time.getMinute());    //  Testing
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        txt = new com.bkzalo.component.Chat_Item();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(txt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
    }
}
