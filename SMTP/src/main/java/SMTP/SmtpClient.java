package SMTP;

import model.mail.Mail;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;

public class SmtpClient implements ISmtpClient{

    private String address;
    private int port;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    public SmtpClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    private void connect() throws IOException {
        socket = new Socket(address, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    private void disconnect() throws IOException {
        writer.flush();
        writer.close();
        reader.close();
        socket.close();
    }

    public void sendMail(Mail mail) throws IOException {
        connect();
        
        String line = reader.readLine();
    
        writer.println("EHLO ping");
        writer.flush();

        while (!(line = reader.readLine()).startsWith("250 "));

        writer.println("MAIL FROM: <" + mail.getFrom() + ">");
        writer.flush();

        line = reader.readLine();

        for(String to : mail.getTo()){
            writer.println("RCPT TO: <" + to + ">");
            writer.flush();
            line = reader.readLine();
        }

        writer.println("DATA");
        writer.flush();

        line = reader.readLine();
    
        writer.println("From: " + mail.getFrom());
        writer.flush();

        writer.print("To: ");
        for(String to : mail.getTo()){
            writer.print(to + ",");
            writer.flush();
        }


        writer.print("\r\n");
        writer.println(mail.getSubject());
        writer.println("Content-Type: text/plain; charset=utf-8");
        writer.println(mail.getBody());
        writer.println(".");
        writer.flush();

        disconnect();
    }

}
