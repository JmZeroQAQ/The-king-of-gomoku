package com.tkog.botsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

@Component
public class Consumer extends Thread {
    private Bot bot;
    private long timeout;
    private static RestTemplate restTemplate;
    private final static String botMoveUrl = "http://127.0.0.1:3000/game/bot/move/";

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, Bot bot) {
        this.bot = bot;
        this.timeout = timeout - 1000;
        this.start();

        try {
            this.join(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();
        }
    }

    private void writeFile(String filename, String content) {
        File file = new File(filename);
        try(PrintWriter fout = new PrintWriter(file)) {
            fout.println(content);
            fout.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private int readPosition() {
        File file = new File("output");
        int position;
        try {
            Scanner in = new Scanner(file);
            position = in.nextInt();
            in.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if(file.exists()) file.delete();

        return position;
    }

    @Override
    public void run() {
        // 将输入和代码写入文件
        writeFile("/home/jmzero/app/code/input", this.bot.getInput());
        writeFile("/home/jmzero/app/code/code.cpp", this.bot.getBotCode());

        Process process;
        try {
            process = Runtime.getRuntime().exec("./start.sh");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            // 最多等待进程 timeout 毫秒
            process.waitFor(this.timeout, TimeUnit.MILLISECONDS);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            process.destroy();
        }

        // 如果脚本运行的没有问题,就将输出结果发送回去
        if(process.exitValue() == 0) {
            int position = readPosition();
            MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
            data.add("user_id", bot.getUserId().toString());
            data.add("position", Integer.toString(position));

            restTemplate.postForObject(botMoveUrl, data, String.class);
        }

    }
}
