package com.kpi;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

public class ReverseFileTask implements Runnable {
    private BlockingQueue<File> queue;
    private String outputDirectory;
    public ReverseFileTask(BlockingQueue<File> queue, String outputDirectory) {
        this.queue = queue;
        this.outputDirectory = outputDirectory;
    }

    @Override
    public void run() {
        try {
            while (true) {
                File file = queue.take();
                if (file == JavaFilesSearchTask.exit) {
                    queue.put(file);
                    break;
                } else {
                    reverseFile(file);
                }
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    private void reverseFile(File file) {
        try (BufferedReader sourceFile = new BufferedReader(new FileReader((file)))) {

            File outputFile = new File(Paths.get(outputDirectory, file.getPath()).toString());
            Files.createDirectories(outputFile.getParentFile().toPath());
            boolean created = outputFile.createNewFile();
            if (created) {
                System.out.println("Writing from file " + file + " to " + outputFile);
                try (BufferedWriter destination = new BufferedWriter(new FileWriter(outputFile))) {
                    String line;
                    while ((line = sourceFile.readLine()) != null) {
                        destination.write(new StringBuffer(line).reverse() + "\n");
                    }
                }
            } else {
                System.out.println("Unable to create file:" + outputFile);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
