import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // Object of a class that has both produce()
        // and consume() methods
        final Watki watki = new Watki();
        try {
            FileReader plik1 = new FileReader(new File("src/plik1.txt"));
            FileReader plik2 = new FileReader(new File("src/plik2.txt"));
            FileReader plik3 = new FileReader(new File("src/plik3.txt"));
            FileWriter plik4 = new FileWriter(new File("src/plik4.txt"));

        // Create writing thread
        Thread p1 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    watki.watekCzytajacy(plik1, 0);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        Thread p2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    watki.watekCzytajacy(plik2, 1);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        // Create reading thread
        Thread p3 = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    watki.watekCzytajacy(plik3, 2);
                }
                catch(InterruptedException e)
                {
                    e.printStackTrace();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

            Thread p4 = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        watki.watekPiszacy(plik4);
                    }
                    catch(InterruptedException e)
                    {
                        e.printStackTrace();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            });


        // Start both threads
        p1.start();
        p2.start();
        p3.start();
        p4.start();
        //t2.start();

        // t1 finishes before t2
        p1.join();
        p2.join();
        p3.join();
        p4.join();
        //t2.join();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
