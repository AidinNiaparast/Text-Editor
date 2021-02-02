package project;

import java.awt.Color;
import java.awt.Graphics;


public class MyChar {
    char ch;
    public MyChar(char ch){
        this.ch=ch;
    }
    public void paint(Graphics g,int x,int y,boolean isSelected){
        String str=new String();
        str+=ch; 
        if(isSelected==false){
            g.setColor(Color.black);
            g.drawString(str, x-2, y);
        }
        else{
            g.setColor(Color.blue);
            g.fillRect(x-2,y-15,Frame.CHAR_WIDTH,Frame.CHAR_HEIGHT);
            g.setColor(Color.white);
            g.drawString(str, x-2, y);
        }
    }
}
