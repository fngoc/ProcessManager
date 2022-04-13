package com.example.processmanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class ProcessChecker {

    private final String CHECK_CMD;
    private final String WHAT_FIND;
    private final String WHAT_I_DO;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessChecker.class);

    @Autowired
    public ProcessChecker(ApplicationArguments arguments) {
        String[] args = arguments.getSourceArgs();
        this.CHECK_CMD = args[0];
        this.WHAT_FIND = args[1];
        this.WHAT_I_DO = args[2];
    }

    @Scheduled(fixedDelay=5000)
    public void check() {
        StringBuilder outCMD = new StringBuilder();
        Runtime r = Runtime.getRuntime();
        Process p = null;

        try {
            p = r.exec(CHECK_CMD);
            p.getOutputStream().close();

            InputStream processStdOutput = p.getInputStream();
            Reader r2 = new InputStreamReader(processStdOutput);
            BufferedReader br = new BufferedReader(r2);
            String line;
            while ((line = br.readLine()) != null) {
                outCMD.append(line);
            }
            p.waitFor();

            if (new String(outCMD).contains(WHAT_FIND)) {
                LOGGER.info("Все хорошо сервис " + WHAT_FIND + " работает");
            } else {
                LOGGER.warn("Что-то пошло не так, перезапускаю сервис " + WHAT_FIND);
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
