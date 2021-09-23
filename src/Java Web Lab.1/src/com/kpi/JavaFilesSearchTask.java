package com.kpi;

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class JavaFilesSearchTask implements Runnable {
    private BlockingQueue<File> queue;
    private File currentDirectory;
    private static final String javaFileRegex = ".*\\.java";
    public static final File exit = new File("");

    public JavaFilesSearchTask(BlockingQueue<File> queue, File currentDirectory) {
        this.queue = queue;
        this.currentDirectory = currentDirectory;
    }

    @Override
    public void run() {
        try {
            searchDirectory(currentDirectory);
            queue.put(exit);
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }

    private void searchDirectory(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                searchDirectory(file);
            } else if (file.getName().matches(javaFileRegex)) {
                queue.put(file);
            }
        }
    }
}
