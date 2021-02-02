package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Frame extends JFrame {
    LinkedList <Line> myText;
    
    Line[] clipboard,selectedText;
    
    int cursorRow=0,cursorCol=0;
    int x=15,y=50;
    static int firstShownRow=0,firstShownCol=0;
    final static int CHAR_WIDTH=12,CHAR_HEIGHT=20;
    boolean isCtrlOn=false,isShiftOn=false,isInsOn=false;
    static boolean isSelected=false,haveBeenSaved=false;
    static int firstSelectedRow=0;

	static int firstSelectedColumn=0;
    static int lastSelectedRow=0,lastSelectedColumn=0;
    boolean showLineNumberMode=false;
    String searchedWord;
    boolean changeSelectedText;
   
    
    public Frame(){
        myText=new LinkedList<>();
        setBounds(100,100,500,500);
        setVisible(true);
        setDefaultCloseOperation(Frame.EXIT_ON_CLOSE);
        setFont(new Font("Anonymous Pro",Font.PLAIN, 15));
        setFocusTraversalKeysEnabled(false);
    }
    
    
    public static void ff(String s){
        System.out.println(s);
    }
    
    @Override
    public int getWidth(){
        Dimension windowSize = this.getContentPane().getSize();
        return windowSize.width;
    }
    
    @Override
    public int getHeight(){
        Dimension windowSize = this.getContentPane().getSize();
        return windowSize.height;
    }
    
    public int maxCharsCapacity(){
        return (this.getWidth()-15)/CHAR_WIDTH;
    }
    
    public int maxLinesCapacity(){
        return (this.getHeight()-50)/CHAR_HEIGHT;
    }
    
    public void addLine(){
        Line e=new Line();
        myText.add(e);
    }
    
    public void addLine(int index){
        Line e=new Line();
        myText.add(index, e);
    }
    
    public void backspace(){
        try{
            if(this.cursorCol!=0){
                this.myText.get(this.cursorRow).remove(this.cursorCol-1);
                this.cursorCol--;
                this.repaint();
            }
            else{
                if(this.cursorRow!=0){
                    int newCursorCol=this.myText.get(this.cursorRow-1).size();
                    this.myText.get(this.cursorRow-1).addAll(this.myText.get(this.cursorRow));
                    this.myText.remove(this.cursorRow);
                    this.cursorRow--;
                    this.cursorCol=newCursorCol;
                    this.repaint();
                }
            }
        }
        catch(Exception ex){
        }
    }

    public void enter(){

        this.addLine(this.cursorRow+1);
        this.cursorRow++;
        this.myText.get(this.cursorRow).addAll(this.cursorCol,this.myText.get(this.cursorRow-1).size(),this.myText.get(this.cursorRow-1));
        this.myText.get(this.cursorRow-1).removeAll(this.cursorCol,this.myText.get(this.cursorRow-1).size());
        this.cursorCol=0;
        this.repaint();
    }
    
    public void lArrow(){
        if(this.cursorCol==0){
            if(this.cursorRow!=0){
                this.cursorRow--;
                this.cursorCol=this.myText.get(this.cursorRow).size();
            }
            return;
        }
        this.cursorCol--;
    }
    
    public  void rArrow(){
         if(this.cursorCol==this.myText.get(this.cursorRow).size()){
             if(this.cursorRow!=this.myText.size()-1){
                 this.cursorRow++;
                 this.cursorCol=0;
             }
             return;
         }
         this.cursorCol++;
     }

    public  void uArrow(){
         if(this.cursorRow!=0){
             this.cursorRow--;
             if(this.cursorCol>this.myText.get(this.cursorRow).size())
                 this.cursorCol=this.myText.get(this.cursorRow).size();
         }
     }

    public  void dArrow(){
         if(this.cursorRow!=this.myText.size()-1){
             this.cursorRow++;
             if(this.cursorCol>this.myText.get(this.cursorRow).size())
                 this.cursorCol=this.myText.get(this.cursorRow).size();
         }
     }

    public void delete(){
        if (this.cursorRow==this.myText.size()-1 && this.myText.get(myText.size()-1).size()==this.cursorCol)
           return;
        else{
            this.rArrow();
            this.backspace();
        }
    }
     
    public  void home(){
         this.cursorCol=0;
     }

    public  void ctrlHome(){
         this.cursorCol=0;
         this.cursorRow=0;
     }

    public  void end(){
        this.cursorCol=this.myText.get(this.cursorRow).size();
    }   

    public void ctrlEnd(){
        this.cursorRow=this.myText.size()-1;
        this.cursorCol=this.myText.get(myText.size()-1).size();
    }
    
    public void ctrlD(){
        myText.get(cursorRow).removeAll(0,myText.get(cursorRow).size());
        cursorCol=0;
    }
    
    public void ctrlA(){
        firstSelectedRow=firstSelectedColumn=0;
        lastSelectedRow=myText.size()-1;
        lastSelectedColumn=myText.get(lastSelectedRow).size();
        isSelected=true;
    }
    
    public void ctrlLeft() {
        if(cursorRow==0&&cursorCol==0)  return;
        if(cursorCol==0){
            cursorRow--;
            cursorCol=myText.get(cursorRow).size();
            return;
        }
        if(myText.get(cursorRow).get(cursorCol-1).ch!=' '){
            while(myText.get(cursorRow).get(cursorCol-1).ch!=' '&&cursorCol>0){
                cursorCol--;
                if(cursorCol==0)    return;
            }
            return;
        }
        if(myText.get(cursorRow).get(cursorCol-1).ch==' '){
            while(myText.get(cursorRow).get(cursorCol-1).ch==' '&&cursorCol>0){
                cursorCol--;
                if(cursorCol==0)    return;
            }
            if(myText.get(cursorRow).get(cursorCol-1).ch!=' '){
                while(myText.get(cursorRow).get(cursorCol-1).ch!=' '&&cursorCol>0){
                    cursorCol--;
                    if(cursorCol==0)  return;
                }
            }
        }
    }

    public void ctrlRight() {
        if(myText.get(cursorRow).size()==cursorCol&&myText.size()-1==cursorRow)
            return;

        if(myText.get(cursorRow).size()==cursorCol){
            cursorRow+=1;
            cursorCol=0;
            return;
        } 
        else{
            if(myText.get(cursorRow).get(cursorCol).ch!=' '){
                while(myText.get(cursorRow).size()>cursorCol && myText.get(cursorRow).get(cursorCol).ch!=' '){
                    cursorCol++;
                    if(cursorCol==myText.get(cursorRow).size())     return;
                }

                while(myText.get(cursorRow).get(cursorCol).ch==' ' && myText.get(cursorRow).size()-1>cursorCol)
                    cursorCol++;
            }

            if (myText.get(cursorRow).get(cursorCol).ch==' '){
                while(myText.get(cursorRow).get(cursorCol).ch==' ' && myText.get(cursorRow).size()>cursorCol){
                    cursorCol++;
                    if(cursorCol==myText.get(cursorRow).size())
                        return;
                }
                if(myText.get(cursorRow).get(cursorCol).ch!=' '){
                    while (myText.get(cursorRow).size()>cursorCol && myText.get(cursorRow).get(cursorCol).ch!=' '){
                        cursorCol++;
                        if(cursorCol==myText.get(cursorRow).size())   return;
                    }

                    while(myText.get(cursorRow).get(cursorCol).ch==' ' && myText.get(cursorRow).size()>cursorCol){
                        cursorCol++;
                        if(cursorCol==myText.get(cursorRow).size())
                            return;
                    }
                }
            }
        }
    }
    
    public void pageDown(){
        if(myText.size()-cursorRow<=this.maxLinesCapacity())
            return;
        
        cursorRow+=this.maxLinesCapacity()+1;
        cursorRow=Math.min(cursorRow,myText.size()-1);
        
        cursorCol=Math.min(cursorCol,myText.get(cursorRow).size());
        firstShownRow+=this.maxLinesCapacity()+1;      
        
        if(myText.size()>this.maxLinesCapacity()&&myText.size()-firstShownRow<this.maxLinesCapacity()){
            firstShownRow=myText.size()-this.maxLinesCapacity();
        }
    }
    
    public void pageUp(){
        if(cursorRow<=this.maxLinesCapacity()){
            firstShownRow=0;
            cursorRow=0;
            return;
        }
        cursorRow-=this.maxLinesCapacity()+1;
        cursorRow=Math.max(0,cursorRow);
        cursorCol=Math.min(cursorCol,myText.get(cursorRow).size());
    }
    
    public void goToLineNumber(int lineNumber){
        if(lineNumber>myText.size())    return;
        cursorRow=lineNumber-1;
        cursorCol=0;
    }
    
    public void fixScreen(){
        if(cursorCol-firstShownCol>this.maxCharsCapacity())
            firstShownCol+=Math.max(Math.min(16,this.myText.get(cursorRow).size()-cursorCol+1),cursorCol-firstShownCol-this.maxCharsCapacity());
        if(cursorCol<firstShownCol)
            firstShownCol-=Math.max(Math.min(16,cursorCol+1),firstShownCol-cursorCol);
        if(cursorRow-firstShownRow>this.maxLinesCapacity())
            firstShownRow+=Math.max(1,cursorRow-firstShownRow-this.maxLinesCapacity());
        if(cursorRow<firstShownRow)
            firstShownRow-=Math.max(1,firstShownRow-cursorRow);
    }
  
    public static int[] fixSelectedText(){
        if(lastSelectedRow>firstSelectedRow||(lastSelectedRow==firstSelectedRow&&lastSelectedColumn>=firstSelectedColumn))
            return new int[]{firstSelectedRow,firstSelectedColumn,lastSelectedRow,lastSelectedColumn};
        return new int[]{lastSelectedRow,lastSelectedColumn,firstSelectedRow,firstSelectedColumn};
    }  
    
    public static boolean isThisCharSelected(int row,int col){
        int [] order=fixSelectedText();
        
        int firstSelectedRow=order[0];
        int firstSelectedColumn=order[1];
        int lastSelectedRow=order[2];
        int lastSelectedColumn=order[3];

   
        if(row>firstSelectedRow&&row<lastSelectedRow)
            return true;
        if(firstSelectedRow==lastSelectedRow){
            if(col>=firstSelectedColumn&&col<lastSelectedColumn&&row==firstSelectedRow)
                return true;
            else
                return false;
        }
        
        if(row==firstSelectedRow&&firstSelectedColumn<=col)
            return true;
        
        if(row==lastSelectedRow&&lastSelectedColumn>col)
            return true;
            
        return false;
    }
    
    public void eraseSelectedText(){
        if(isSelected==false)   return;
        
        int [] order=fixSelectedText();
        
        int firstSelectedRow=order[0];
        int firstSelectedColumn=order[1];
        int lastSelectedRow=order[2];
        int lastSelectedColumn=order[3];
        
        if(firstSelectedRow==lastSelectedRow){
            myText.get(firstSelectedRow).removeAll(firstSelectedColumn,lastSelectedColumn);
            cursorCol=firstSelectedColumn;
        }
        else{
            for(int i=firstSelectedRow+1;i<lastSelectedRow;i++)
                myText.remove(firstSelectedRow+1);
            myText.get(firstSelectedRow).addAll(myText.get(firstSelectedRow+1));
            myText.get(firstSelectedRow).removeAll(firstSelectedColumn,myText.get(firstSelectedRow).size()-myText.get(firstSelectedRow+1).size()+lastSelectedColumn);
            myText.remove(firstSelectedRow+1);
            
            
            cursorRow=firstSelectedRow;
            cursorCol=firstSelectedColumn;
        }
    }
    
    public Line[] copySelectedText(){
        int [] order=fixSelectedText();
        
        int firstSelectedRow=order[0];
        int firstSelectedColumn=order[1];
        int lastSelectedRow=order[2];
        int lastSelectedColumn=order[3];
        
        Line[] ret=new Line[lastSelectedRow-firstSelectedRow+1];
        
        for(int i=firstSelectedRow;i<=lastSelectedRow;i++){
            ret[i-firstSelectedRow]=new Line();
            ret[i-firstSelectedRow].addAll(myText.get(i));
            /*for(int j=0;j<myText.get(i).size();j++)
                ret[i-firstSelectedRow].add(ret[i-firstSelectedRow].size(),myText.get(i).myText.get(j).ch);
            */
        }
        
        ret[lastSelectedRow-firstSelectedRow].removeAll(lastSelectedColumn,myText.get(lastSelectedRow).size());
        ret[0].removeAll(0,firstSelectedColumn);
        
        return ret;
    }
    
    public void pasteText(Line[] text,int positionRow,int positionCol){
        if(text.length==1){
            for(int i=0;i<text[0].size();i++)
                myText.get(positionRow).add(positionCol+i,text[0].myLine.get(i).ch);
        }
        else{
            //int sizeOfPositionRow=myText.get(positionRow).size();
            //int sizeOfLastSelectedRow=text[text.length-1].size();
            
            
            //text[text.length-1].addAmyText(positionCol,myText.get(positionRow).size(),myText.get(positionRow));
            Line temp=new Line();
            temp.addAll(text[text.length-1]);
            temp.addAll(positionCol,myText.get(positionRow).size(),myText.get(positionRow));
            myText.get(positionRow).removeAll(positionCol,myText.get(positionRow).size());
            myText.get(positionRow).addAll(text[0]);
            for(int i=1;i<text.length-1;i++)
                myText.add(positionRow+i,text[i]);
            myText.add(positionRow+text.length-1,temp);
            //text[text.length-1].removeAmyText(sizeOfLastSelectedRow+positionCol,sizeOfLastSelectedRow+sizeOfPositionRow);
        }
    }
    
    public int lengthOfSelectedText(){
        int sum=0;
        if(lastSelectedRow==firstSelectedRow)
            sum=lastSelectedColumn-firstSelectedColumn;
        else{
            sum+=myText.get(firstSelectedRow).size()-firstSelectedColumn;
            for(int i=firstSelectedRow+1;i<lastSelectedRow;i++)
                sum+=myText.get(i).size();
            sum+=lastSelectedColumn;
        }
        return sum;
    }
    
    public boolean checkWord(int row,int col,String word){
        if(col+word.length()-1>myText.get(row).size())   
            return false;
        
        for(int j=col;j<col+word.length();j++)
            if(myText.get(row).get(j).ch!=word.charAt(j-col))
                return false;
       
        return true;
    }
    
    public boolean findNext(String word){
        for(int j=cursorCol;j<myText.get(cursorRow).size();j++){
            if(checkWord(cursorRow, j, word)){
                isSelected=true;
                firstSelectedRow=cursorRow;
                firstSelectedColumn=j;
                lastSelectedRow=cursorRow;
                lastSelectedColumn=j+word.length();
                cursorRow=lastSelectedRow;
                cursorCol=lastSelectedColumn;
                return true;
            }
        }
        
        for(int i=cursorRow+1;i<myText.size();i++){
            for(int j=0;j<myText.get(i).size();j++){
                if(checkWord(i, j, word)){
                    isSelected=true;
                    firstSelectedRow=i;
                    firstSelectedColumn=j;
                    lastSelectedRow=i;
                    lastSelectedColumn=j+word.length();
                    cursorRow=lastSelectedRow;
                    cursorCol=lastSelectedColumn;
                    return true;
                } 
            }
        }
        
        isSelected=false;
        return false;
    }
    
    public void replace(String first,String second){
        cursorRow=cursorCol=0;
        while(findNext(first)){
            eraseSelectedText();
            for(int i=0;i<second.length();i++)
                myText.get(cursorRow).add(cursorCol+i,second.charAt(i));
            cursorCol+=second.length();
        }
    }
    
    public Line strToLine(String input) {
        Line res = new Line();
        for (int i = 0; i < input.length(); i++) {
            res.add(i, input.charAt(i));
        }
        return res;
    }
    
    public String toString(Line l){
        String res="";
        for (int i=0;i<l.size();i++){
            res+=l.get(i).ch;
        }
        return res;
    }
    
    public void saveFile(Frame frm) throws IOException {
        JFileChooser fileChooser = new JFileChooser();

        if (fileChooser.showSaveDialog(frm) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // save to file
            OutputStream os = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(os);
            for (int i = 0; i < myText.size(); i++) {
                writer.write(this.toString(myText.get(i)));
                writer.write("\n");
            }
            writer.flush();
            writer.close();
//            System.out.println("finished saving!");

        }
        isCtrlOn=false;

    }

    public void openFile(Frame frm) throws  IOException{
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        if (fileChooser.showOpenDialog(frm) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            // load from file
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String txt = "";
                int k=0;
                for (int i=0;i<myText.size();i++){
                    myText.remove(0);
                }
                while ((txt = reader.readLine()) != null){
                    System.out.println(txt);

                    myText.add(k,strToLine(txt));
                    k++;
                }
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        isCtrlOn=false;
    }

    public void newFile(Frame frm)  throws  IOException{
        try{
            Line l=new Line();
            this.saveFile(frm);
            myText.clear();
            myText=new LinkedList<Line>();
            myText.add(l);
        }
        catch (Exception e){
        }
        cursorCol=0;
        cursorRow=0;
        isCtrlOn=false;
    }
    
    @Override
    public void paint(Graphics g) {
        
        //super.paint(g);
        
        g.setColor(Color.WHITE);
        g.fillRect(0, 0,this.getWidth(),this.getHeight());
        
        this.fixScreen();
        this.fixSelectedText();
        
        String lineSpace="";
       
        for(int i=firstShownRow;i<myText.size()&&(i-firstShownRow)*CHAR_HEIGHT<=this.getHeight()-50;i++){
            if(showLineNumberMode){
                lineSpace+=i+1;
                lineSpace+=" ";
            }
            
            g.setColor(Color.black);
            g.drawString(lineSpace,x,y+CHAR_HEIGHT*(i-firstShownRow));
            myText.get(i).paint(g,x+12*(lineSpace.length()), y+CHAR_HEIGHT*(i-firstShownRow),this.getWidth(), i);
            
            lineSpace = "";
            
            //myText.get(i).paint(g,x,y+CHAR_HEIGHT*(i-firstShownRow),this.getWidth(),i);
        }
        
        g.setColor(Color.black);
        
        lineSpace+=cursorRow+1;
        if(showLineNumberMode)
            g.drawRect(x+(cursorCol-firstShownCol)*CHAR_WIDTH-4+CHAR_WIDTH*(lineSpace.length()+1),y+CHAR_HEIGHT*(cursorRow-firstShownRow)-15,1,19);
        else
            g.drawRect(x+(cursorCol-firstShownCol)*CHAR_WIDTH-4, y+CHAR_HEIGHT*(cursorRow-firstShownRow)-15,1,19);
    }    
}