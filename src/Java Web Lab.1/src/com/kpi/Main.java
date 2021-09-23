package com.kpi;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        int numOfTasks = 5;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the source directory:");
        String sourceDirectory = scanner.nextLine();

        while (!new File(sourceDirectory).exists()) {
            System.out.println("Enter the existing source directory");
            sourceDirectory = scanner.nextLine();
        }

        System.out.println("Enter the output directory:");
        String outputDirectory = scanner.nextLine();

        while (new File(outputDirectory).exists()) {
            System.out.println("Output directory is not empty, choose another one:");
            outputDirectory = scanner.nextLine();
        }

        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(100);
        Runnable searchTask = new JavaFilesSearchTask(queue, new File(sourceDirectory));
        ExecutorService pool = Executors.newFixedThreadPool(numOfTasks);
        Future[] futures = new Future[numOfTasks];
        Future searchFuture = pool.submit(searchTask);
        try {
            searchFuture.get();
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex);
        }
        for (int i = 0; i < numOfTasks; i++) {
            futures[i] = pool.submit(new ReverseFileTask(queue, outputDirectory));
        }

        for (int i = 0; i < numOfTasks; i++) {
            try {
                futures[i].get();
            } catch (InterruptedException | ExecutionException ex) {
                System.out.println(ex);
            }
        }
        pool.shutdown();
    }
}
