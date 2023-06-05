// Import Java RMI Library
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.*;

public class ChatServerimplement extends UnicastRemoteObject implements ChatServer
{
    // Create a Hash Map with Name and Client Object
    private Map<String,ChatClient> client_map=new HashMap<>();

   // Empty Constructor
    public ChatServerimplement() throws RemoteException { }
    
    // Login method to add a Client to the Map
    public String[] login(ChatClient client) throws RemoteException
    {
        String name_cl = client.getName();
        // Check if the Client name Already Exists
        if(client_map.containsKey(name_cl))
        {
            throw new RuntimeException("Name already Exists");
        }
	// Adding each client to the Map
        String[] cl_names = list();
        client_map.put(name_cl,client);
        for(String clientname: cl_names)
        {
            client_map.get(clientname).joined(name_cl);
        }
        return cl_names;
    }
    // To return Client Name List
    public String[] list()
    {
        return client_map.keySet().toArray(new String[client_map.size()]);
    }
    // To show messages that are communicated from one Client to Another
    public void send_Msg(String from, String to, String msg) throws RemoteException
    {
        client_map.get(to).show_msg(from, msg);
    }
    // To Broadcast a Message to all Client
    public void send_Msg(String from, String msg) throws RemoteException
    {
        String[] cl_names=list();
        for(String clientname: cl_names)
        {
            send_Msg(from,clientname,msg);
        }
    }
    // To keep track of Clients who are not active
    public void logout(String name) throws RemoteException
    {
	// To remove a Client from Map
        client_map.remove(name);
        String[] cl_names=list();
        for(String clientname: cl_names)
        {
            client_map.get(clientname).left(name);
        }
    }
}
