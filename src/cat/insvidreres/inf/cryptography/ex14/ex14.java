package cat.insvidreres.inf.cryptography.ex14;

import cat.insvidreres.inf.cryptography.utils.IsmaUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ex14 {


    public static void main(String[] args) {
        String createCommand = IsmaUtils.createKeyCmd("test15Key");
        String getKeyCommand = IsmaUtils.printSpecificKeyCmd("test15Key");

        try {
            Process process1 = Runtime.getRuntime().exec(createCommand);
            int createProcessExitCode = process1.waitFor();

            if (createProcessExitCode == 0) {
                System.out.println("The key has been created successfully");

                Process listProcess = Runtime.getRuntime().exec(getKeyCommand);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(listProcess.getInputStream()));
                String line;

                System.out.println("-------------------------");
                System.out.println("    DADES CLAU CREADA    ");

                while ((line = bufferedReader.readLine()) != null) {
                    System.out.println(line);
                }
                System.out.println("-------------------------");

            }
            else
                System.out.println("Error in creating the key, exit code: " + createProcessExitCode);

        } catch (InterruptedException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        try {
            Process process2 = Runtime.getRuntime().exec(getKeyCommand);
            int getProcessExitCode = process2.waitFor();

            if (getProcessExitCode == 0)
                System.out.println("The key has been retrieved!");
            else
                System.out.println("Error in getting the key, exit code: " + getProcessExitCode);

        } catch (InterruptedException | IOException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
