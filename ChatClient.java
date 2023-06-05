
// Import RMI Libraries 
import java.rmi.Remote;
import java.rmi.RemoteException;

// Interface for Client
public interface ChatClient extends Remote
{
    // Methods defined (getName, joined, left, show_msg)

    // To get Name
    String getName() throws RemoteException;

    // To indicate the Server that the Client joined 
    void joined(String name) throws RemoteException;

    // To indicate the Server that the Client has logged out 
    void left(String name) throws RemoteException;

    // To display the message received
    void show_msg(String from, String msg) throws RemoteException;
}

