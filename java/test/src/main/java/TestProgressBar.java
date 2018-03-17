import java.io.IOException;

public class TestProgressBar {
    public static void main(String[] args) {
        progressBarB();
    }

    private static void progressBarA() {
        try {
            String anim= "|/-\\";
            for (int x =0 ; x < 100 ; x++){
                String data = "\r" + anim.charAt(x % anim.length())  + " " + x ;
                System.out.write(data.getBytes());
                Thread.sleep(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void progressBarB() {
        try {
            StringBuilder progressBar = new StringBuilder();
            for (int x =0 ; x <= 100 ; x++){
                String data = buildProgressBar(progressBar, x);
                System.out.write(data.getBytes());
                Thread.sleep(100);
            }
            System.out.println("\ndone!\n");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static String buildProgressBar(StringBuilder progressBar, int percent) {
        progressBar.append("\r").append("|");
        for (int i = 0; i < percent; i++) {
            progressBar.append("=");
        }

        int whitespaceLength = 100 - percent;
        for (int i = 0; i < whitespaceLength; i++) {
            progressBar.append(" ");
        }
        progressBar.append("|");
        progressBar.append(" ").append(percent).append("%");
        return progressBar.toString();
    }
}
