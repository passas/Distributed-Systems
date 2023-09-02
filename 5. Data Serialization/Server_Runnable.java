import java.io.DataOutputStream;
import java.io.DataInputStream;
import java.net.Socket;

public class Server_Runnable implements Runnable
{
    private Socket socket;
    private ContactManager contact_manager;

    public Server_Runnable (Socket socket, ContactManager manager)
    {
        this.socket = socket;
        this.contact_manager = manager;
    }

    // @TODO
    @Override
    public void run()
    {
        //Server Input
        DataInputStream input;
        Contact contact;
        //Server Output
        DataOutputStream output;

        try
        {
            //Set-up server input
            input = new DataInputStream ( socket.getInputStream() );
            //Set-up server output
            output = new DataOutputStream ( socket.getOutputStream() );
            
            //send contact list
    
            while ( (contact = Contact.deserialize( input )) != null )
            { //client is up
                this.contact_manager.update ( contact );
                //Client sendind
                contact.serialize ( output );   
            }
            input.close();
            output.close();
            socket.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
