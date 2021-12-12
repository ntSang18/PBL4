package com.bkzalo.component;

import javax.swing.Icon;
import javax.swing.GroupLayout.Alignment;

public class Image_Item extends javax.swing.JLayeredPane {
	
    private com.bkzalo.swing.PictureBox pic;

    public Image_Item() {
        initComponents();
    }

    public void setImage(Icon image) {
        pic.setImage(image);
    }

    public void setImageReceive() { //Model_Receive_Image dataImage
//        int width = dataImage.getWidth();
//        int height = dataImage.getHeight();
//        int[] data = BlurHash.decode(dataImage.getImage(), width, height, 1);
//        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
//        img.setRGB(0, 0, width, height, data, 0, width);
//        Icon icon = new ImageIcon(img);
//        pic.setImage(icon);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        pic = new com.bkzalo.swing.PictureBox();

        javax.swing.GroupLayout picLayout = new javax.swing.GroupLayout(pic);
        picLayout.setHorizontalGroup(
        	picLayout.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 450, Short.MAX_VALUE)
        );
        picLayout.setVerticalGroup(
        	picLayout.createParallelGroup(Alignment.LEADING)
        		.addGap(0, 300, Short.MAX_VALUE)
        );
        pic.setLayout(picLayout);

        setLayer(pic, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pic, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }
}
