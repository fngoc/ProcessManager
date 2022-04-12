package com.example.processmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ProcessChecker {

    private final String CMD_CHECK;

    private final String FIND;

    private final String WHAT_I_DO;

    @Autowired
    public ProcessChecker(ApplicationArguments arguments) {
        this.CMD_CHECK = arguments.getSourceArgs()[0];
        this.FIND = arguments.getSourceArgs()[1];
        this.WHAT_I_DO = arguments.getSourceArgs()[2];
    }

    @Scheduled(fixedDelay=5000)
    public void check() {
        StringBuilder outCMD = new StringBuilder();
        Runtime r = Runtime.getRuntime();
        Process p = null;

        try {
            p = r.exec(CMD_CHECK);
            p.getOutputStream().close();

            InputStream processStdOutput = p.getInputStream();
            Reader r2 = new InputStreamReader(processStdOutput);
            BufferedReader br = new BufferedReader(r2);
            String line;
            while ((line = br.readLine()) != null) {
                outCMD.append(line);
            }
            p.waitFor();

            if (new String(outCMD).contains(FIND)) {
                System.out.println("Все хорошо сервис " + FIND + " работает");
            } else {
                System.out.println("Что-то пошло не так, перезапускаю сервис " + FIND);
                startService();
            }
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
    }

    private void startService() {
        try {
            Process proc = Runtime.getRuntime().exec(WHAT_I_DO);
            proc.waitFor();
            proc.destroy();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
