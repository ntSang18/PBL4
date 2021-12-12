package com.bkzalo.component;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.io.File;

import javax.swing.JScrollBar;

import com.bkzalo.app.MessageType;
import com.bkzalo.emoji.Emogi;
import com.bkzalo.model.Model_Message;
import com.bkzalo.service.Service;
import com.bkzalo.swing.ScrollBar;
import com.bkzalo.component.Chat_Date;
import com.bkzalo.component.Chat_Left;
import com.bkzalo.component.Chat_Left_With_Profile;
import com.bkzalo.component.Chat_Right;

import net.miginfocom.swing.MigLayout;

public class Chat_Body extends javax.swing.JPanel {

    private javax.swing.JPanel body;
    private javax.swing.JScrollPane sp;
    
    public Chat_Body() {
        initComponents();
        init();
    }

    private void init() {
        body.setLayout(new MigLayout("fillx", "", "5[bottom]5"));
        sp.setVerticalScrollBar(new ScrollBar());
        sp.getVerticalScrollBar().setBackground(Color.WHITE);
    }

    public void addItemLeft(Model_Message mgs){
        if (mgs.getType() == MessageType.TEXT) {
            Chat_Left item = new Chat_Left();
            item.setText(mgs.getText());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        } else if (mgs.getType() == MessageType.EMOJI) {
            Chat_Left item = new Chat_Left();
            item.setEmoji(Emogi.getInstance().getImoji(Integer.valueOf(mgs.getText())).getIcon());
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        } 
        else if (mgs.getType() == MessageType.IMAGE) {
            Chat_Left item = new Chat_Left();
            item.setText("");
            if(mgs.getFile() != null) {
            	item.setImage(mgs.getFile());
            }
            else {
            	String fileExtention = mgs.GetFileExtention();
            	File file = new File(Service.Instance().PATH_FILE_RECEIVE + mgs.getId() + fileExtention);
            	item.setImage(file);
            }
            item.setTime();
            body.add(item, "wrap, w 100::80%");
        }
        else if(mgs.getType() == MessageType.FILE) {
        	Chat_Left item = new Chat_Left();
        	String fileExtention = mgs.GetFileExtention();
        	File file = new File(Service.Instance().PATH_FILE_RECEIVE + mgs.getId() + fileExtention);
        	item.setFile(mgs.getFilename(), String.valueOf(file.length()));
        	item.setTime();
            body.add(item, "wrap, w 100::80%"); 
        }
        repaint();
        revalidate();
        scrollToBottom();
    }
    
    public void addItemRight(Model_Message mgs) {
        if (mgs.getType() == MessageType.TEXT) {
            Chat_Right item = new Chat_Right();
            item.setText(mgs.getText());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");
        } 
        else if (mgs.getType() == MessageType.EMOJI) {
            Chat_Right item = new Chat_Right();
            item.setEmoji(Emogi.getInstance().getImoji(Integer.valueOf(mgs.getText())).getIcon());
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%");
        }
        else if (mgs.getType() == MessageType.IMAGE) {
            Chat_Right item = new Chat_Right();
            item.setText("");
            if(mgs.getFile() != null) {
            	item.setImage(mgs.getFile());
            }
            else {
            	String fileExtention = mgs.GetFileExtention();
            	File file = new File(Service.Instance().PATH_FILE_SEND + mgs.getId() + fileExtention);
            	item.setImage(file);
            }
            item.setTime();
            body.add(item, "wrap, al right, w 100::80%"); 
        }
        else if(mgs.getType() == MessageType.FILE) {
        	Chat_Right item = new Chat_Right();
        	if(mgs.getFile() != null) {
        		item.setFile(mgs.getFilename(), String.valueOf(mgs.getFile().length()));
        	}
        	else {
        		String fileExtention = mgs.GetFileExtention();
            	File file = new File(Service.Instance().PATH_FILE_SEND + mgs.getId() + fileExtention);
            	item.setFile(mgs.getFilename(), String.valueOf(file.length()));
        	}
        	item.setTime();
            body.add(item, "wrap, al right, w 100::80%"); 
        }
        repaint();
        revalidate();
        scrollToBottom();
    }

    public void addDate(String date) {
        Chat_Date item = new Chat_Date();
        item.setDate(date);
        body.add(item, "wrap, al center");
        body.repaint();
        body.revalidate();
    }

    public void clearChat() {
        body.removeAll();
        repaint();
        revalidate();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        body = new javax.swing.JPanel();

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        body.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout bodyLayout = new javax.swing.GroupLayout(body);
        body.setLayout(bodyLayout);
        bodyLayout.setHorizontalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
        );
        bodyLayout.setVerticalGroup(
            bodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 555, Short.MAX_VALUE)
        );

        sp.setViewportView(body);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sp)
        );
    }

    private void scrollToBottom() {
        JScrollBar verticalBar = sp.getVerticalScrollBar();
        AdjustmentListener downScroller = new AdjustmentListener() {
            @Override
            public void adjustmentValueChanged(AdjustmentEvent e) {
                Adjustable adjustable = e.getAdjustable();
                adjustable.setValue(adjustable.getMaximum());
                verticalBar.removeAdjustmentListener(this);
            }
        };
        verticalBar.addAdjustmentListener(downScroller);
    }
}
