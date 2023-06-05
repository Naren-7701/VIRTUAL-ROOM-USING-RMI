// Import RMI Libraries
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

// Import Java Abstract Window Toolkit Libraries
import java.awt.List;
import java.awt.TextArea;

public class ChatClientGUIimplement extends UnicastRemoteObject implements ChatClient
{
    // Each Client has a name,Client List,Chat Area
    private String name;
    private List clienList;
    private TextArea chatArea;

    // Constructor 
    public ChatClientGUIimplement(String name, List l, TextArea ar) throws RemoteException
    {
        this.name=name;
        this.clienList = l;
        this.chatArea = ar;
    }
 
    // Gets the Name of Client from GUI Frame
    public String getName()
    {
        return this.name;
    }

    // The Client adds itself to the List of Clients present in the Chat Room
    public void joined(String name)
    {
        this.clienList.add(name);
    }

    // The Client removes its name from the Client List once it logs out
    public void left(String name)
    {
        this.clienList.remove(name);
    }

    // The Client Displays the message received from Server or other Clients
    public void show_msg(String from, String msg)
    {
        this.chatArea.append("Message from: "+from+": "+msg+"\n");
    }
}

