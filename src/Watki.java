import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

public class Watki {

    private LinkedList<String> list = new LinkedList<>();
    private int capacity = 1, czytaj[] = {0, 0, 0};
    int wri = 0;
    private int lol;
    private char buffer[] = new char[capacity];
    private boolean check = false;

    public void watekCzytajacy(FileReader plik, int num) throws InterruptedException, IOException {
        while (czytaj[num] != -1) {
            synchronized (this) {
                while(list.size() == 1 || check == true){
                    wait();
                    //System.out.println("czekam " + plik.getEncoding() + ' ' + iter++);
                    //System.out.println("czekam "+ num);
                }


                check = true;

                if (buffer[0] == ' ') buffer[0] = 's';

                lol = num;

                while(buffer[0] != ' ' && czytaj[num] != -1) {
                    list.add("FULL");
                    czytaj[num] = plik.read(buffer);
                    wri = czytaj[num];
                    notifyAll();
                    wait();
                }

                check = false;
                notifyAll();
                Thread.sleep(200);

                if(Thread.activeCount() == 4 && czytaj[num] == -1){
                    list.add("full");
                    wri = czytaj[num];
                    notifyAll();
                }
            }
        }
    }


    // Function called by consumer thread
    public void watekPiszacy(FileWriter plik) throws InterruptedException, IOException {
        while (Thread.activeCount() > 3) {
            synchronized (this) {

                while(list.size() == 0)
                    wait();
                System.out.println("Obsluguje " + lol + " Zapisanych ");
                Thread.sleep(200);

                list.clear();
                if(wri != -1)
                    plik.write(buffer);
                notifyAll();

            }
        }
        plik.close();
    }
}


