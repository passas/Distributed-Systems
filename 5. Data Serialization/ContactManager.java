import java.util.Map;
import java.util.HashMap;

import java.util.Arrays;

public class ContactManager
{
    private HashMap<String, Contact> contacts;

    public ContactManager()
    {
        this.contacts = new HashMap<>();
        
        //simulation
        this.update(new Contact("John", 20, 253123321, null, Arrays.asList("john@mail.com")));
        this.update(new Contact("Alice", 30, 253987654, "CompanyInc.", Arrays.asList("alice.personal@mail.com", "alice.business@mail.com")));
        this.update(new Contact("Bob", 40, 253123456, "Comp.Ld", Arrays.asList("bob@mail.com", "bob.work@mail.com")));
    }

    public void update(Contact c)
    {
    	this.contacts.put ( c.name(), c );
    }

    public ContactList getContacts()
    {
    	ContactList r;

    	r = new ContactList ();
    	for (Contact c : this.contacts.values())
    		r.add ( c );

    	return r;
    }
}
