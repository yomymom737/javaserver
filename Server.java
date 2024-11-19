import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server 
{
    private int portNo;
    private ServerSocket serverSocket;
    private ArrayList<Socket> sockets;

    public Server()
    {
        portNo = 7777;
        sockets = new ArrayList<Socket>();
    }

    public void Connect()
    {
        try 
        {
            serverSocket = new ServerSocket(portNo);
            while(true)
            {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                System.out.println("Connected: " + socket.getInetAddress().getHostName());
                ServerThread thread = new ServerThread(socket, sockets);
                new Thread(thread).start();
            }
        } 
        catch (Exception e) 
        {
            System.err.println("SuperException " + e.getMessage());
        }
    }

    public void Disconnect()
    {
        try 
        {
            if (serverSocket != null)
            {
                serverSocket.close();
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
    }
}
