package com.bkzalo.component;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;

import com.bkzalo.event.PublicEvent;
import com.bkzalo.component.Image_Item;

import net.miginfocom.swing.MigLayout;

public class Chat_Image extends javax.swing.JLayeredPane {

    public Chat_Image(boolean right) {
        initComponents();
        setLayout(new MigLayout("", "0[" + (right ? "right" : "left") + "]0", "3[]3"));
    }

    public void addImage(File file) {
        Icon image = new ImageIcon(file.getAbsolutePath());
        Image_Item pic = new Image_Item();
        pic.setPreferredSize(getAutoSize(image, 200, 200));
        pic.setImage(image); //, fileSender
        addEvent(pic, image);
        add(pic, "wrap");
    }

    public void addImageReveive() {//Model_Receive_Image dataImage
//        Image_Item pic = new Image_Item();
//        pic.setPreferredSize(new Dimension(dataImage.getWidth(), dataImage.getHeight()));
//        pic.setImage(dataImage);
//        //  addEvent(pic, image);
//        add(pic, "wrap");
    }

    private void addEvent(Component com, Icon image) {
        com.setCursor(new Cursor(Cursor.HAND_CURSOR));
        com.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                	System.out.println("Chat_Image.addEvent");
                    PublicEvent.getInstance().getEventImageView().viewImage(image);
                }
            }
        });
    }

    private Dimension getAutoSize(Icon image, int w, int h) {
        if (w > image.getIconWidth()) {
            w = image.getIconWidth();
        }
        if (h > image.getIconHeight()) {
            h = image.getIconHeight();
        }
        int iw = image.getIconWidth();
        int ih = image.getIconHeight();
        double xScale = (double) w / iw;
        double yScale = (double) h / ih;
        double scale = Math.min(xScale, yScale);
        int width = (int) (scale * iw);
        int height = (int) (scale * ih);
        return new Dimension(width, height);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }
}
