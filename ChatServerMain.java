// Import Java RMI Registry Library to store RMI Objects
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;

// Main method to implement Virtual Room
public class ChatServerMain
{
    public static void main(String[] args) throws RemoteException
    {
	// Virtual Room name from Command Line 
        String virtroom=args[0];
	// To Create a RMI Registry with a Port number (Default Port Number:1099)
        Registry reg= LocateRegistry.createRegistry(1099);
        ChatServer server= new ChatServerimplement();
	// To bind the Virtual Room and Server
        reg.rebind(virtroom, server);
    }
}
