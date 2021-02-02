package project;

import java.awt.Graphics;
import java.util.LinkedList;

public class Line{
    LinkedList <MyChar> myLine;
    public Line(){
        myLine=new LinkedList<MyChar>();
    }
    
    public void add(char ch){
        MyChar myCh=new MyChar(ch);
        myLine.add(myCh);
    }
    
    public void add(int ind,char ch){
        MyChar myCh=new MyChar(ch);
        myLine.add(ind,myCh);
    }

    public void remove(int index){
        myLine.remove(index);
    }
    
    public int size(){
        return myLine.size();
    }
    
    public MyChar get(int index){
        return myLine.get(index);
    }
    
    public void addAll(Line l){
        for(int i=0;i<l.size();i++)
            myLine.add(l.get(i));
    }
    
    public void addAll(int start,int end,Line l){
        for(int i=start;i<end;i++)
            myLine.add(l.get(i));
    }
    
    public void removeAll(int start,int end){
        for(int i=start;i<end;i++)
            myLine.remove(start);
    }
 
    
    public void paint(Graphics g,int x,int y,int screenWidth,int lineNumber){
        for(int i=Frame.firstShownCol;i<myLine.size()&&(i-Frame.firstShownCol)*Frame.CHAR_WIDTH<=screenWidth-15;i++){
            if(Frame.isSelected && Frame.isThisCharSelected(lineNumber,i)==true)
                myLine.get(i).paint(g,x+(i-Frame.firstShownCol)*Frame.CHAR_WIDTH,y,true);
            else
                myLine.get(i).paint(g,x+(i-Frame.firstShownCol)*Frame.CHAR_WIDTH,y,false);
        }
    }
}