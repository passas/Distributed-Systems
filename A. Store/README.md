### Journaling ###

This is an example of 2 politics: client-center vs server-center (full x mid-term).

This is not worth my effort; as long as missing definitions of porpose.

It is worth to catch that a ReentrantLock is still an object afterwhile, and it's associated conditions can be multi-single distributed through existing objects of a Collection.


### Where is my fucking ticket ?! ###
Thread-0 # supply Chupeta

Thread-1 # NULL Anel

Thread-1 # NULL Bola

Thread-1 # consuming Chupeta

Thread-1 # consumed Chupeta

Thread-2 # NULL Anel

Thread-2 # NULL Bola

Thread-2 # consuming Chupeta

**Thread-2 # waiting for Chupeta**

Thread-4 # supply Anel

**Thread-3 # supply Chupeta**

Thread-6 # supply Bola

Thread-5 # supply Chupeta

Thread-7 # consuming Anel

Thread-7 # consumed Anel

Thread-7 # consuming Bola

Thread-7 # consumed Bola

***Thread-7*** **# consuming Chupeta**

Thread-7 # consumed Chupeta

Thread-8 # consuming Anel

Thread-8 # waiting for Anel

Thread-9 # consuming Anel

Thread-9 # waiting for Anel

Thread-10 # consuming Anel

Thread-10 # waiting for Anel

Thread-11 # consuming Anel

Thread-11 # waiting for Anel

Thread-12 # supply Bola

Thread-13 # supply Chupeta

Thread-15 # supply Chupeta

Thread-14 # supply Anel

Thread-16 # supply Anel

Thread-17 # consuming Anel

Thread-17 # consumed Anel

Thread-17 # consuming Bola

Thread-17 # consumed Bola

Thread-17 # consuming Chupeta

Thread-17 # consumed Chupeta

**Thread-2 # consumed Chupeta**

Thread-8 # consumed Anel

Thread-8 # consuming Bola

Thread-8 # waiting for Bola

Thread-9 # waiting for Anel

Thread-10 # waiting for Anel

Thread-11 # waiting for Anel
