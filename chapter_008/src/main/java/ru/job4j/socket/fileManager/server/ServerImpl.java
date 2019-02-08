package ru.job4j.socket.fileManager.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.stream.Stream;

public class ServerImpl {
    private final static Settings SETTINGS = Settings.getInstance();
    private final Socket socket;
    private final String homePath;
    private String currentDirectory;
    private boolean working = true;

    public ServerImpl(Socket socket) {
        this.homePath = SETTINGS.value("homeDirectory");
        this.currentDirectory = this.homePath;
        this.socket = socket;
    }

    public void start() {
        try (DataOutputStream out = new DataOutputStream(this.socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()))) {
            String ask;
            do {
                System.out.println("wait command ...");
                ask = in.readLine();
                System.out.println(ask);
                switch (ask) {
                    case ("goHome"): {
                        System.out.println(this.getList(this.homePath));
                        out.writeBytes(this.getList(this.homePath));
                        this.currentDirectory = this.homePath;
                        break;
                    }
//                    case ("goTo"): {
//                        String path = in.readLine();
//                        out.writeUTF(this.goTo(path));
//                        break;
//                    }
//                    case ("download"): {
//                        String name = in.readUTF();
//                        out.writeUTF(this.downloadFile(name));
//                        break;
//                    }
//                    case ("push"): {
//                        String name = in.readUTF();
//                        String text = in.readUTF();
//                        out.writeUTF(this.pushFile(name, text));
//                        break;
//                    }
                }
                if ("exit".equals(ask)) {
                    this.working = false;
                }
            } while (this.working);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getList(String path) {
        StringBuilder string = new StringBuilder();
        File file = new File(path);
        String[] files = file.list();
        if (files != null) {
            if (files.length == 0) {
                string.append("Directory is empty");
            } else {
                Stream.of(files).forEach(nameFile -> {
                            string.append(nameFile);
                            string.append("\r\n");
                        }
                );
                string.append("!!!");
            }
        } else {
            string.append("Error!");
        }
        return string.toString();
    }

    private String goTo(String path) {
        String rsl;
        String toDirect = this.currentDirectory + "/" + path;
        File directory = new File(toDirect);
        if (directory.isDirectory()) {
            this.currentDirectory = toDirect;
            rsl = getList(this.currentDirectory);
        } else {
            rsl = path + " is not directory!";
        }
        return rsl;
    }

    private String downloadFile(String name) {
        StringBuilder result = new StringBuilder();
        File file = new File(this.currentDirectory + "/" + name);
        if (!file.isDirectory()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                    result.append(System.getProperty("line.separator"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            result.append("Loading error");
        }
        return result.toString();
    }

    private String pushFile(String name, String text) {
        File file = new File(this.currentDirectory + "/" + name);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "success";
    }


    public static void main(String[] args) {
        int port = Integer.valueOf(SETTINGS.value("port"));
        try (Socket socket = new ServerSocket(port).accept()) {
            ServerImpl server = new ServerImpl(socket);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
