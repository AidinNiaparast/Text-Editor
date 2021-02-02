package project;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;

public class Listener implements KeyListener {
    Frame frm;
    
    public Listener(){
    }
    
    public Listener(Frame f) {
        frm = f;
        frm.addLine();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
        switch (e.getKeyChar()){
            case 8:
                //backspace
                frm.backspace();
                break;

            case 9:
                //tab
                for(int i=0;i<4;i++)
                    frm.myText.get(frm.cursorRow).add(frm.cursorCol,' ');
                frm.cursorCol+=4;
                break;

            case 10:
                //enter
                frm.enter();
                break;

            case 127:
                frm.delete();
                break;    
                
            default:
                if(frm.isCtrlOn==false){
                    if(frm.isInsOn){
                        if(frm.cursorCol!=frm.myText.get(frm.cursorRow).size()){
                            frm.rArrow();
                            frm.backspace();
                        }
                    }
                    frm.myText.get(frm.cursorRow).add(frm.cursorCol,e.getKeyChar());
                    frm.cursorCol++;
                    frm.repaint();
                }
        }
        
        //System.out.println(e.getExtendedKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        frm.changeSelectedText=true;
        
        if(e.getKeyCode()==16){
            //shift
            if(frm.isSelected==false){
                frm.firstSelectedRow=frm.cursorRow;
                frm.firstSelectedColumn=frm.cursorCol;
            }
            frm.isShiftOn=true;
            frm.isSelected=true;
            return;
        }
        
        if(e.getKeyCode()==17){
            //ctrl
            frm.isCtrlOn=true;
            return;
        }
        
        
        switch(e.getKeyCode()){
            case 37:
                //left arrow
                if(frm.isCtrlOn)
                    frm.ctrlLeft();
                else
                    frm.lArrow();
                break;

            case 39:
                //right arrow
                if(frm.isCtrlOn)
                    frm.ctrlRight();
                else
                    frm.rArrow();
                break;

            case 38:
                //up arrow
               frm.uArrow();
                break;

            case 40:
                //down arrow
               frm.dArrow();
                break;


            case 36:
                if(frm.isCtrlOn)
                    frm.ctrlHome();
                else
                    frm.home();
                break;
            
            case 35:
                if(frm.isCtrlOn)
                    frm.ctrlEnd();
                else
                    frm.end();
                break;

            case 34:
                frm.pageDown();
                break;

            case 33:
                frm.pageUp();
                break;    
            
            case 155:
                //ins
                frm.isInsOn=!frm.isInsOn;
                break;    
                
            case 68:
                //d
                if(frm.isCtrlOn)
                    frm.ctrlD();
                break;
            
            case 76:
                //l
                if(frm.isCtrlOn)
                    frm.showLineNumberMode=!frm.showLineNumberMode;
                break;
            
            case 75:
                //k
                if(frm.isCtrlOn){
                    Scanner input=new Scanner(System.in);
                    int lineNumber=input.nextInt();
                    frm.goToLineNumber(lineNumber);
                }
                break;
                
            case 65:
                //a
                frm.ctrlA();
                break;
                
            case 67:
                //c
                if(frm.isCtrlOn&&frm.isSelected&&frm.selectedText!=null)
                    frm.clipboard=frm.copySelectedText();
                break;
            
            case 88:
                //x
                if(frm.isCtrlOn&&frm.isSelected&&frm.selectedText!=null){
                    frm.clipboard=frm.copySelectedText();
                    frm.eraseSelectedText();
                    frm.isSelected=false;
                }
                break;
            
            case 86:
                //v
                if(frm.isCtrlOn&&frm.clipboard!=null){
                    if(frm.isSelected)
                        frm.eraseSelectedText();
                    frm.pasteText(frm.clipboard,frm.cursorRow,frm.cursorCol);
                }
                break;
            
            case 70:
                //f
                if(frm.isCtrlOn){
                    Scanner input=new Scanner(System.in);
                    frm.searchedWord=input.next();
                    frm.findNext(frm.searchedWord);
                    frm.changeSelectedText=false;
                }
                break;
            
            case 71:
                //g
                if(frm.isCtrlOn){
                    if(!frm.findNext(frm.searchedWord)){
                        System.out.println("Cannot find \""+frm.searchedWord+"\"");
                    }
                    frm.changeSelectedText=false;
                }
                break;
            
            case 82:
                //r
                if(frm.isCtrlOn){
                    Scanner input=new Scanner(System.in);
                    String first=input.next(),second=input.next();
                    frm.replace(first, second);
                }
                break;
                
            case 78:
                //n
                if (frm.isCtrlOn) {
                    try {
                        frm.newFile(frm);
                    } 
                    catch (Exception e1){
                    }
                }
                break;

            case 79:
                //o
                if (frm.isCtrlOn && Frame.haveBeenSaved) {
                    try {
                        frm.openFile(frm);
                    } 
                    catch (Exception e1){
                    }
                } 
                else if (frm.isCtrlOn){
                    try {
                        frm.saveFile(frm);
                    } 
                    catch (Exception e1){
                    }
                    Frame.haveBeenSaved=true;
                    try {
                        frm.openFile(frm);
                    } 
                    catch (Exception e1){
                    }
                }
                break;
    
            
            case 83:
                //s
                if (frm.isCtrlOn){
                    try {
                        frm.saveFile(frm);
                    } 
                    catch (Exception e1){
                    }
                    Frame.haveBeenSaved=true;
                }
                break;    
        }
        
        if(frm.isShiftOn==false&&frm.isCtrlOn==false&&frm.isSelected==true){
            int key=e.getKeyCode();
            if((key<=20&&key>=18)||key==27||(key>=33&&key<=40)||key==45)
                frm.isSelected=false;
            else{
                frm.selectedText=frm.copySelectedText();
                frm.eraseSelectedText();
                frm.isSelected=false;
            }
        }
        
        if(frm.isShiftOn){
            int key=e.getKeyCode();
            if(key>=33&&key<=40){
                frm.changeSelectedText=true;
                if(!frm.isSelected){
                    frm.isSelected=true;
                    frm.firstSelectedRow=frm.cursorRow;
                    frm.firstSelectedColumn=frm.cursorCol;
                }
            }
            else{
                frm.isSelected=false;
                frm.changeSelectedText=false;
            }
        }
        
        if(frm.changeSelectedText){   
            frm.lastSelectedRow=frm.cursorRow;
            frm.lastSelectedColumn=frm.cursorCol;
        }
        frm.repaint();
        
        //System.out.println(e.getExtendedKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case 17:
                //ctrl
                frm.isCtrlOn=false;
                break;
            case 16:
                //shift
                frm.isShiftOn=false;
                frm.selectedText=frm.copySelectedText();
                break;    
        }
        
        //System.out.println(e.getExtendedKeyCode());
    }
}