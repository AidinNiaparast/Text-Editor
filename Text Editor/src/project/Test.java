package project;

public class Test {
    public static void main(String[] args) {
        Frame test = new Frame();
        test.setTitle("Note Pad");
        Listener l = new Listener(test);
        test.addKeyListener(l);
    }
}