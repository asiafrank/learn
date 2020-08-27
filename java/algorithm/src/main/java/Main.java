import crls.Sorts;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        try {
            while (true) {
                int[] a = {7,6,5,4,3,2,1};
                Sorts.insertSort(a);
                Thread.sleep(1000L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
