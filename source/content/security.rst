.. highlight:: properties

.. index:: kerberos, security

********
Security
********

Secure connection - Kerberos 
****************************

The JAAS framework defines the term **subject** to represent the source of a request. A subject may be any entity, such as a person or service.

*Authentication* represents the process by which the identity of a subject is verified, and must be performed in a secure fashion; otherwise a perpetrator may impersonate others to gain access to a system. Authentication typically involves the subject demonstrating some form of evidence to prove its identity. Such evidence may be information only the subject would likely know or have (such as a password or fingerprint), or it may be information only the subject could produce (such as signed data using a private key). 

A Subject is populated with associated identities, or **Principals** such as name ("John Doe") which distinguish it from other Subjects.

In addition to associated Principals, a Subject may own security-related attributes, which are referred to as **credentials**. A credential may contain information used to authenticate the subject to new services. Such credentials include passwords, Kerberos tickets, and public key certificates.

**Kerberos** is a known authentication mechanism that lets clients authenticate against a single Key Distribution Center (KDC).

In order to do that, some files are required.

	* ``jaas.conf`` (Login configuration file) and
	* ``krb5.conf`` Kerberos congif file
	* ``.keytab`` config binary file to avoid specifing a system property with a password

``jaas.conf`` can be used for both *Server* and *Client* as long as it has one entry for the client side and one for the server side.

================
jaas.conf sample
================

::

	Client {
	  com.sun.security.auth.module.Krb5LoginModule required
			useKeyTab=true
			keyTab="<path_to_file>/<file_name>.keytab"
			useTicketCache=true
			renewTGT=true
			storeKey=true
			principal="<user_name>@your_realm";
	};

	Server {
		com.sun.security.auth.module.Krb5LoginModule required
			useKeyTab=false
			storeKey=true
			useTicketCache=false
			principal="service_principal@your_realm";
	};

*Client* specifies a *principal* so that all clients authenticate using the same user name. Your Kerberos-style **user name** is simply the user name you were assigned for Kerberos authentication. It consists of a base user name (like "mjones") followed by an "@" and your realm (like "mjones@KRBNT-OPERATIONS.ABC.COM"). 

=================
krb5.conf sample
=================

:: 

	[libdefaults]
		default_realm = <realm>
		default_checksum = rsa-md5

	[realms]
		DATASTAX.COM = {
			kdc = <ip_address>
		}

	[domain_realm]
		datastax.com = DATASTAX.COM

===================
Setting up Kerberos
===================

First we need to tell Hector that we want to use Kerberos authentication::

	CassandraHostConfigurator.setUseKerberosAuthentication(true);

Second, we need to let Hector know where the *jaas.conf* and *krb5.conf* files are. In order to do that, let's set the following properties::

	-Djava.security.auth.login.config=<path_to_the_file>/jaas.conf
	-Djava.security.krb5.conf=<path_to_the_file>/krb5.conf
	-Dkerberos.client.reference.name=Client
	-Dkerberos.service.principal.name=service_principal

**Note:** ``service_principal`` should be just the name. I.e: cassandra/host@REALM you should use *cassandra*. 

If *java.security.auth.login.config* and *java.security.krb5.conf* two properties are not set, the default is *./jaas.conf* and *./krb5.conf* respectively from the root of the classpath.

If *kerberos.client.reference.name* is not set, the default is *Client*. This is the reference in the *jaas.conf* file for the client.

Finally, we need to point *jaas.conf* to the right *.keytab* file. Edit the property **keyTab="..."** in *jaas.conf* file.

For more Information, visit: 

	* http://en.wikipedia.org/wiki/Kerberos_%28protocol%29
	* http://download.oracle.com/javase/1.5.0/docs/guide/security/jgss/tutorials/glossary.html
	* http://download.oracle.com/javase/1.5.0/docs/guide/security/jgss/tutorials/ClientServer.html