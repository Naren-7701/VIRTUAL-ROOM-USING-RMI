// Import Java RMI Libraries 
// Import Abstract Window Toolkit Libraries 
// Import javax Libaries for Panel, Button
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.awt.List;
import java.awt.Panel;
import java.awt.Frame;
import java.awt.Button;
import java.awt.TextArea;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import javax.swing.JPanel;
import javax.swing.JButton;

public class ChatFrame extends Frame
{
    // Each Client has the following objects 
    private ChatServer server;
    private ChatClient client;
    private String name;
    // Chat Area Window with No.of Columns and Rows
    private TextArea chatarea=new TextArea(20,70);
    // Client List Window with No.of Columns
    private List client_List=new List(20, true);
    // Text Messgae Typing Area with No.of Columns and Rows
    private TextArea ent_Area=new TextArea(5,70);
    // Button Send and Log out 
    private JButton send_btn=new JButton("SEND");
    private JButton log_btn=new JButton("LOG OUT"); 

    public ChatFrame(ChatServer server, String clientname) throws RemoteException
    {
	// Calls the Parent Class Frame 
        super("GUI Client -"+clientname);
        this.server=server;
        this.name=clientname;
        this.client=new ChatClientGUIimplement(clientname,client_List,chatarea);
	// Get the List of Clients who are active 
        String[] clint_names=server.login(client);
	// Add those Client names to the Client List Panel
        for(String clnt_name:clint_names)
        {
            client_List.add(clnt_name);
        }
	// Set the Size of Chat Frame Window
        this.setBounds(0,0,600,400);
        this.setCompo();
        this.setEvents();
    }
    private void setCompo()
    {
	// Create a New Chat Window
        this.setLayout(new FlowLayout());
	// Add Client Panel, Chat Area, Text Area and Buttons alomg with the Panel 
	this.add(chatarea);
	this.add(client_List);
        this.add(ent_Area);
	// New Panel to Add Buttons (Send,Log_out)
	JPanel Pan = new JPanel(new GridLayout(2,1));		
        Pan.add(send_btn);
        Pan.add(log_btn); 
        this.add(Pan);
    }
    
    private void setEvents()
    {
 	// Call the logout Function when User Clicks X Button of the Chat Frame
        this.addWindowListener(new WindowAdapter() 
        { 
            public void windowClosing(WindowEvent wev)
            {
                logout();
            }
        });
	// Call the logout function when user clicks Log out Button
        this.log_btn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent aev)
            {
                logout();
            }
        });
        // Send the Message to the particular Client when user clicks Send Button
        this.send_btn.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent aev)
        {
            String[] cl_name= client_List.getSelectedItems();
            for(String client_name: cl_name)
            {
                try
                {
                    server.send_Msg(name,client_name,ent_Area.getText());
                    chatarea.append("Send message to: "+client_name+"\n");
                }
		// Exception when Sending Message Failed 
                catch(RemoteException rem)
                {
                    rem.printStackTrace();
                    chatarea.append("Send Message Failed for: "+client_name+"\n");
                }
            }
        }
        });
    }
    // Terminate the Session and Exit the Chat Window 
    private void logout()
    {
        try
        {
            this.server.logout(name);
        }
        catch(RemoteException rex)
        {
            rex.printStackTrace();
        } 
        this.dispose();
        System.exit(0);
    }

    public static void main(String[] args) throws Exception
    {
	// Get Client Name, Host Name (Common to all), Virtual Room name (Common to All)
        String clen_name=args[0];
        String host=args[1];
        String chatRoom=args[2];
	// Create a Server Lookup in the Registry and Bind
	// Create a New Connection with the Server 
        ChatServer server= (ChatServer)Naming.lookup("rmi://"+host+"/"+chatRoom);
        ChatFrame fr=new ChatFrame(server,clen_name);
        fr.setVisible(true);
    }
}
