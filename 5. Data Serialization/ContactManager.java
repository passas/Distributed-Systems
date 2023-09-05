import java.util.Map;
import java.util.HashMap;

import java.util.Arrays;

import java.util.concurrent.locks.ReentrantLock;

public class ContactManager
{

    private ReentrantLock lock;
    private HashMap<String, Contact> contacts;

    public ContactManager()
    {
        this.lock = new ReentrantLock ();
        this.contacts = new HashMap<>();
        
        //simulation
        this.update(new Contact("John", 20, 253123321, null, Arrays.asList("john@mail.com")));
        this.update(new Contact("Alice", 30, 253987654, "CompanyInc.", Arrays.asList("alice.personal@mail.com", "alice.business@mail.com")));
        this.update(new Contact("Bob", 40, 253123456, "Comp.Ld", Arrays.asList("bob@mail.com", "bob.work@mail.com")));
    }

    public void update(Contact c)
    {
        this.lock.lock ();
        try
        {
        	this.contacts.put ( c.name(), c );
        }
        finally
        {
            this.lock.unlock ();
        }
    }

    public ContactList getContacts()
    {
    	ContactList r;

    	r = new ContactList ();
        this.lock.lock ();
        try
        {
        	for (Contact c : this.contacts.values())
        		r.add ( c );
    
        	return r;
        }
        finally
        {
            this.lock.unlock ();
        }
    }
}
