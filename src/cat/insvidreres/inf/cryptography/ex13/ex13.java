package cat.insvidreres.inf.cryptography.ex13;

import cat.insvidreres.inf.cryptography.utils.IsmaUtils;

import java.io.*;

public class ex13 {

    public static void main(String[] args) {
        String command = IsmaUtils.getKeyListCommand();

        try {
            Process process = Runtime.getRuntime().exec(command);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
            process.waitFor();

        } catch (IOException | InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
