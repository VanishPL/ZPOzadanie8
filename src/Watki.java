import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Watki {

    private LinkedList<String> list = new LinkedList<>();
    private int capacity = 1, czytaj = 0;
    private char buffer[] = new char[capacity];
    private boolean check = false;

    public void watekCzytajacy(FileReader plik) throws InterruptedException, IOException {
        while (czytaj != -1) {
            synchronized (this) {

                while(list.size() == 1)
                    wait();

                list.add("FULL");
                czytaj = plik.read(buffer);
                notifyAll();

            }
        }
    }


    // Function called by consumer thread
    public void watekPiszacy(FileWriter plik) throws InterruptedException, IOException {
        while (Thread.activeCount() > 3) {
            synchronized (this) {
                while(list.size() == 0)
                    wait();

                list.clear();
                if(czytaj != -1)
                plik.write(buffer);
                Thread.sleep(240);
                notifyAll();

            }
        }
        plik.close();
    }
}


