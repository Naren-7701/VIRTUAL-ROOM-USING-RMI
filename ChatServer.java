// Import Java RMI Libraries
import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface for Chat Server
public interface ChatServer extends Remote
{
    // Method to keep track of Clients who are active at present
    String[] login(ChatClient client) throws RemoteException;
    
    // To get the List of Clients
    String[] list() throws RemoteException;
    
    // To send message to particular Client
    void send_Msg(String from, String to, String msg) throws RemoteException;

    // To Broadcast Message to All 
    void send_Msg(String from, String msg) throws RemoteException;

    // To know which Client Logs out
    void logout(String name) throws RemoteException;
}

