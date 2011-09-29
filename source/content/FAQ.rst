.. _FAQ:

FAQ
***


SLF4J fun and hijinks: https://github.com/rantav/hector/wiki/SLF4J-in-Hector-
=============================================================================

If you are using Hector in your project and are getting a ClassNotFound exception for an SLF4J class, there is a reason for it. You must include the correct SLF4J binding in your project. Hector's only SLF4J dependency is on the API itself, not the logging implementations. For us (or any library developer for that matter) to make assumptions about which logging library you are using is poor form. 

For more information `see this mail list thread <http://groups.google.com/group/hector-users/browse_thread/thread/aa5d9d34aea0a9df/219acdeb9569b8ac>`_ and `this description <http://www.slf4j.org/codes.html#StaticLoggerBinder>`_ from the SLF4J documentation. 

:sub:`Thanks to Stephen Connolly for getting are pom files straightened out to support this.`

How to compile Hector from sources ?
====================================

	1. Install "Maven2" (http://maven.apache.org) (it's used as build tool)
	2. Install "Git" (http://git-scm.com) Setup proxy-server if need ``git config --global http.proxy "http://username:password@192.168.1.1:3128/"``
	3. Create folder "github" and cd to it. Execute ``git clone https://github.com/rantav/hector.git``
	4. cd to folder *hector*
	5. Execute ``mvn install -Dmaven.test.skip=true -DperformRelease=true``

At folder target you will find last build of Hector :)

About needed runtime jars you find in Rapitano doc.