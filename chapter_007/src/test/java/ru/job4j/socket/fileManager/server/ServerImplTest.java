package ru.job4j.socket.fileManager.server;

import com.google.common.base.Joiner;
import org.junit.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServerImplTest {
    private static final String LN = System.getProperty("line.separator");
    private Socket socket = mock(Socket.class);

    private void testServer(String input, String expected) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        DataInputStream in1 = new DataInputStream(in);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DataOutputStream out1 = new DataOutputStream(out);
        when(this.socket.getInputStream()).thenReturn(in1);
        when(this.socket.getOutputStream()).thenReturn(out1);
        ServerImpl server = new ServerImpl(this.socket);
        server.start();
        assertThat(out.toString(), is(expected));
    }

    @Test
    public void whenSeeHomeDirectory() throws IOException {
        testServer(
                Joiner.on(LN).join("1", "0"),
                Joiner.on(LN).join("directory", "test1.txt", "test2.txt", "", "Bye", "")
        );
    }

    @Test
    public void whenSeeHomeAndOtherDirectories() throws IOException {
        testServer(
                Joiner.on(LN).join("1", "2", "directory", "0"),
                Joiner.on(LN).join("directory", "test1.txt", "test2.txt", "",
                        "directoryText1.txt", "directoryText2.txt", "", "Bye", "")
        );
    }

    @Test
    public void whenSeeDirectoryAndDownloadTextThere() throws IOException {
        testServer(
                Joiner.on(LN).join("2", "directory", "3", "directoryText1.txt", "0"),
                Joiner.on(LN).join("directoryText1.txt", "directoryText2.txt", "",
                        "Hello\r\n" +
                                "from\r\n" +
                                "directoryText1", "", "Bye", "")
        );
    }

    @Test
    public void whenPushAndDownloadTextFromDirectory() throws IOException {
        testServer(
                Joiner.on(LN).join("4", "pushedText.txt",
                        "Hello from pushedText", "3", "pushedText.txt", "0"),
                Joiner.on(LN).join("success", "",
                        "Hello from pushedText", "", "Bye", "")
        );
    }

}