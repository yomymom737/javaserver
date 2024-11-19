import java.io.InputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ServerThread implements Runnable
{
    private Socket mySocket;
    private ArrayList<Socket> allSockets;
    static private Object lock = new Object();

    public ServerThread(Socket socket, ArrayList<Socket> sockets)
    {
        this.mySocket = socket;
        this.allSockets = sockets;
    }

    public void run()
    {
        try 
        {
            String welcome = "Welcome " + mySocket.getInetAddress().getHostName() + "\n";
            mySocket.getOutputStream().write(welcome.getBytes());
            while (true)
            {
                InputStream is = mySocket.getInputStream();
                byte[] buffer = is.readNBytes(1);
                synchronized (lock)
                {
                    for (Socket socket : allSockets)
                    {
                        if (socket != mySocket)
                        {
                            socket.getOutputStream().write(buffer);
                        }
                    }
                }
            }
        } catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        
    }
}
