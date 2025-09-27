import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server
{

    public Consumer<Socket> getConsumer()
    {
        return (clientSocket)->{
            try{
                PrintWriter printWriter =new PrintWriter(clientSocket.getOutputStream());
                printWriter.println("Hello from serverSide");
                printWriter.close();
                clientSocket.close();

            }
            catch(IOException ex)
            {
                ex.printStackTrace();
            }
        };
    }
    public static void main(String args[]) throws IOException
    {
        int port=8030;
        
        try{
            ServerSocket socket=new ServerSocket(port);
            Server ob=new Server();
            socket.setSoTimeout(10000);
            System.out.println("Server is listening on port "+port);
            while(true)
            {
                Socket acceptedSocket=socket.accept();
                Thread thread=new Thread(()->ob.getConsumer().accept(acceptedSocket));
                thread.start();
                socket.close();
            }
            
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        

    }
}