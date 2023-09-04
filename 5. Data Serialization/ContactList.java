import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

class ContactList extends ArrayList<Contact>
{

    public void serialize (DataOutputStream out) throws IOException
    {
        out.writeInt ( this.size() );
        for (Contact c : this)
            c.serialize ( out );
    }

    public static ContactList deserialize (DataInputStream in) throws IOException
    {
        int N;
        ContactList contacts;

        contacts = new ContactList ();

        N = in.readInt();
        for (int i=0; i<N; i++)
            contacts.add ( Contact.deserialize (in) );

        return contacts;
    }

}
