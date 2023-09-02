import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

class Contact {

    private String name;
    private int age;
    private long phoneNumber;
    private String company;     // Pode ser null
    private ArrayList<String> emails;

    public Contact (String name, int age, long phoneNumber, String company, List<String> emails)
    {
        this.name = name;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.company = company;
        if (emails != null)
            this.emails = new ArrayList<>(emails);
        else
            this.emails = new ArrayList <> ();
    }

    public String name()
    {
        return name;
    }
    
    public int age()
    {
        return age;
    }
    
    public long phoneNumber()
    {
        return phoneNumber;
    }
    
    public String company()
    {
        return company;
    }
    
    public List<String> emails()
    { 
       if (this.emails != null)
            return new ArrayList<>(emails);
        else
            return new ArrayList <> ();
    }

    // @TODO
    public void serialize (DataOutputStream out) throws IOException
    {
        out.writeUTF ( this.name );

        out.writeInt ( this.age );

        out.writeLong ( this.phoneNumber );

        if ( this.company != null )
        {
            out.writeBoolean ( true );
            out.writeUTF ( this.company );
        }
        else
        {
            out.writeBoolean ( false );
        }
        
        out.writeInt ( this.emails.size() );
        for (String email : this.emails)
            out.writeUTF ( email );
    }

    public static Contact deserialize (DataInputStream in) throws IOException
    {   
        Contact contact;

        String name;
        int age;
        long phoneNumber;
        String company;
        int n_emails;
        List<String> emails;

        try
        {
            name = in.readUTF ();

            age = in.readInt ();

            phoneNumber = in.readLong ();

            if ( in.readBoolean() == true )
            {
                company = in.readUTF ();
            }
            else
            {
                company = null;
            }

            emails = new ArrayList <> ();
            n_emails = in.readInt ();
            for (int i=0; i<n_emails; i++)
                emails.add ( in.readUTF() );

            contact = new Contact (name, age, phoneNumber, company, emails);
        }
        catch (IOException e)
        {
            contact = null;
        }

        return contact;
    }

    public String toString ()
    {
        StringBuilder builder = new StringBuilder();
        
        builder.append(this.name).append(" ");
        builder.append(this.age).append(" ");
        builder.append(this.phoneNumber).append(" ");
        builder.append(this.company).append(" ");
        builder.append(this.emails.toString());
        
        return builder.toString();
    }

}
