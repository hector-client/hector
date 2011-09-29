.. highlight:: java

.. index:: HOM, EntityManager, Annotations

Hector Object Mapper
********************


Overview
========

The primary goal of this module is to map objects (POJOs) to/from Cassandra data storage.  I call it Hector Object Mapper, but we are striving and working toward a title of "JPA 1.0 Basic".  Currently Inheritance is supported as well as some custom annotations that allow custom type conversion.

Start with :ref:`Using the EntityManager <using_entity_manager>` to get a basic idea of how to map objects to/from Cassandra.  The :ref:`HOM-FAQ <hom_faq>` is, well, an FAQ.

All code in the examples can be found in object-mapper module in hector's source repository and is intended to work. so feel free to download and play.


Versioning
==========

1.X-?? --> 0.8.Y-??

2.0-?? --> master (no release yet)



.. _using_entity_manager: 

Using Entity Manager
====================

The easiest way to use the library is to instantiate an EntityManagerImpl and call its persist and find methods.  The EntityManager will scan the package provided to the constructor looking for POJOs annotated with ``@Entity``.  Any POJO with ``@Entity`` will be usable by the EntityManager for saving and loading objects.  This method of persistence is very much in line with the JPA standard.

As an example consider the following POJO::


	package com.mycompany;

	// imports omitted

	@Entity
	@Table(name="TestColumnFamily")
	public class MyPojo {
	  @Id
	  private UUID id;

	  @Column(name="lp1")
	  private long longProp1;

	  @me.prettyprint.hom.annotations.Column(name = "color", converter = ColorConverter.class)
	  private Colors color;

	  private Map<String, String> anonymousProps = new HashMap<String, String>();

	  @AnonymousPropertyAddHandler
	  public void addAnonymousProp(String name, String value) {
	    anonymousProps.put(name, value);
	  }

	  @AnonymousPropertyCollectionGetter
	  public Collection<Entry<String, String>> getAnonymousProps() {
	    return anonymousProps.entrySet();
	  }

	  public String getAnonymousProp(String name) {
	    return anonymousProps.get(name);
	  }

	  public UUID getId() {
	    return id;
	  }

	  public void setId(UUID id) {
	    this.id = id;
	  }

	  public long getLongProp1() {
	    return longProp1;
	  }

	  public void setLongProp1(long longProp1) {
	    this.longProp1 = longProp1;
	  }

	  public Colors getColor() {
	    return color;
	  }

	  public void setColor(Colors color) {
	    this.color = color;
	  }
	}


Notice the @Entity and @Table annotating MyPojo.  @Entity merely signals the EntityManager to make the bean available for persistence management.  @Table defines what ColumnFamily to use when loading and saving.  If @Table is omitted the name of the class is used as the ColumnFamily.  Unlike JPA, properties are not mapped to a ColumnFamily column by default.  To persist a property it must be annotated with @Column, and the name of the column is required.  Also notice I have used two @Column annotations, one a part of JPA and a custom one that uses converters (See :ref:`Custom Property Converters <hom_custom_property_converters>`) to provide custom type conversion to/from byte[], which is required by Cassandra.

So with an annotated POJO it is very easy to save and load an object::


	package com.mycompany;

	// imports omitted

	public class MainProg {

	  public static void main(String[] args) {
	    Cluster cluster = HFactory.getOrCreateCluster("TestPool", "localhost:9160");
	    Keyspace keyspace = HFactory.createKeyspace("TestKeyspace", cluster);

	    try {
	      EntityManagerImpl em = new EntityManagerImpl(keyspace, "com.mycompany");

	      MyPojo pojo1 = new MyPojo();
	      pojo1.setId(UUID.randomUUID());
	      pojo1.setLongProp1(123L);
	      pojo1.setColor(Colors.RED);

	      em.save(pojo1);

	      // do some stuff

	      MyPojo pojo2 = em.load(MyPojo.class, pojo1.getId());

	      // do some more stuff

	      System.out.println("Color = " + pojo2.getColor());
	    } finally {
	      cluster.getConnectionManager().shutdown();
	    }
	  }
	}


That's the basics!  Not much to it.  See :ref:`Custom Property Converters <hom_custom_property_converters>` and :ref:`Anonymous Properties <hom_annonymous_properties>` for advanced configuration.

Inheritance
===========

Inheritance in *Hector Object Mapper* (HOM) is JPA style inheritance.  Where JPA has several ways to implement inheritance, HOM only works with "single table" - meaning all derivations of the base class are stored in a single ColumnFamily row.  At this time I feel this is sufficient because there is no rigid schema defined in Cassandra.  Each object only stores in a Cassandra "row" what it needs.  In traditional RDBMS there is a good argument for having multiple inheritance strategies because of wasting space or rows becoming too large to accommodate all possible derivations.

So let's start with an example modeling furniture.  We have a chair, a table, and a couch - each a type of furniture.  So we'll create four classes; Chair, Table, and Couch, each derived from a base class, Furniture::

	package com.mycompany.furniture;

	// imports omitted

	@Entity
	@Table(name="Furniture")
	@Inheritance
	@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
	public abstract class Furniture {
	  @Id
	  private int id;

	  @Column(name="material")
	  private String material;

	  @Column(name="color")
	  private String color;

	  //  getters/setters required, but not showing to conserve space
	  
	}

	package com.mycompany.furniture;

	// imports omitted

	@Entity
	@DiscriminatorValue("chair")
	public class Chair extends Furniture {

	  @Column(name="recliner")
	  private boolean recliner;

	  @Column(name="arms")
	  private boolean arms;

	  //  getters/setters required, but not showing to conserve space
	 
	}

	package com.mycompany.furniture;

	// imports omitted

	@Entity
	@DiscriminatorValue("table")
	public class BasicTable extends Furniture {

	  @Column(name="extendable")
	  private boolean extendable;

	  @Column(name="shape")
	  private String shape;

	  //  getters/setters required, but not showing to conserve space
	    
	}

	package com.mycompany.furniture;

	// imports omitted

	@Entity
	@DiscriminatorValue("couch")
	public class Couch extends Furniture {

	  @Column(name="foldOutBed")
	  private boolean foldOutBed;

	  @Column(name="numCushions")
	  private int numCushions;

	  //  getters/setters required, but not showing to conserve space

	}

	package com.mycompany.furniture;

	// imports omitted

	@Entity
	@DiscriminatorValue("table_desk")
	public class Desk extends BasicTable {

	  @Column(name="numDrawers")
	  private int numDrawers;

	  //  getters/setters required, but not showing to conserve space

	}


With single table inheritance all derivations of the class hierarchy are persisted in the same ColumnFamily.  The base class defines the ColumnFamily using @Table, and it is illegal to define @Table in any of its derived classes.  There can be multiple levels of inheritance as long as each is annotated with @Entity and each defines a unique DiscriminatorValue (See class, Desk, above.)

The new annotations to discuss are:
	* **@Inheritance** : defines base class of inheritance hierarchy
	* **@DiscriminatorColumn** : the column name to use for discriminating between class types
	* **@DiscriminatorValue** : a unique value for each class in the hierarchy

**@Inheritance** is required by the base class of the hierarchy and signals the mapper to persist the set of classes in the hierarchy based on the strategy.  @Inheritance can specify an inheritance "strategy", but at this moment, only JPA style "single table" storage is available.

**@DiscriminatorColumn** is required by the base class of the hierarchy and defines the column name for storing a discriminator value.  A type can also be specified (see Furniture class) for the column value.  The acceptable values are defined by the enumeration, DiscriminatorType (STRING is the default.)  (You may notice that the discriminator column is not defined as a POJO property anywhere in the hierarchy, even though it *is* stored in the ColumnFamily.  This is by design because it is not needed from an application client perspective.)

**@DiscriminatorValue** is required by all classes in the hierarchy and defines the class' unique value within the hierarchy.  The one exception is if the base class is abstract.  This value is stored in the DiscriminatorColumn when the object is saved.  It is used to determine the class type when loading data from Cassandra.

Saving
------
Saving is no different than non-inheritance.  Use the EntityManager to save the object and as long as the classes are properly configured, everything will simply work!::


	Keyspace keyspace = HFactory.createKeyspace("TestKeyspace", cluster);
	entityMgr = new EntityManagerImpl(keyspace, "com.mycompany.furniture");
	Desk desk = new Desk();
	desk.setId(4);
	desk.setMaterial("pressBoard");
	desk.setColor("black");
	desk.setExtendable(false);
	desk.setShape("rectangle");
	desk.setNumDrawers(2);
	entityMgr.persist(desk);


The above code sample will save to the ColumnFamily Furniture, row key = 4, the following columns:  material, color, extendable, shape, numDrawers, and type.  The discriminator column, type, will have a value of "table_desk".

Loading
--------
To load the Desk object saved above is also very simple.  The following code will use the EntityManager to load a piece of furniture::


	Keyspace keyspace = HFactory.createKeyspace("TestKeyspace", cluster);
	entityMgr = new EntityManagerImpl(keyspace, "com.mycompany.furniture");
	Furniture furniturePiece = entityMgr.load(Furniture.class, 4);
	System.out.println( "class type = " + furniturePiece.getClass().getName());


Java collections: Mapping a POJO
================================

HOM can map a POJO property that is a Java Collection to columns.

In previous versions of HOM, a POJO containing a Java Collection property could not be easily mapped to Cassandra columns using HOM.  You would have needed to create a custom converter that would handle the mapping ... until now.  Now you can annotate a collection property using @Column just like any other property and HOM will do "the right thing".

How does it work?  Each element of the collection is mapped to a Cassandra column with a simple naming scheme, "&lt;pojo-property-name&gt;:&lt;index&gt;".  There is also an "informational column" persisted, "&lt;pojo-property-name&gt;", that contains the type of the Collection and its size.  The elements of the Collection are serialized using Java's Object Serialization, so any type that implements Serializable should work just fine.

A consequence of persisting Collections is that any previously persisted POJO properties are removed and persisted again.  This is because HOM tries to keep persisting fast, and for most cases (if not all) simply removing everything and persisting again, saves a read to get the existing Collection information.  When reading, all columns are read and the "informational column" tells HOM what Collection to to instantiate and how many columns exist. 

Let's look at an example::

	@Entity
	@DiscriminatorValue("table_desk")
	public class Desk extends BasicTable {

	  @Column(name = "desk_type")
	  private String deskType;

	  @Column(name = "drawerList")
	  private List<Drawer> drawerList = new ArrayList<Drawer>();

	  public List<String> getDrawerList() {
	    return drawerList;
	  }

	  public void setDrawerList(List<String> drawerList) {
	    this.drawerList = drawerList;
	  }

	  public String getDeskType() {
	    return deskType;
	  }

	  public void setDeskType(String deskType) {
	    this.deskType = deskType;
	  }
	}


As you can see above, there is a "List" Collection property, drawerList.  Let's assume there are three elements in the list.  HOM will persist four columns:  drawerList, drawerList:0, drawerList:1, drawerList:2.  drawerList contains *java.util.ArrayList* and size of 3.  A custom type, Drawer, is used and will be persisted property using Java Serialization.  Pretty simple!

Note that the implementation does not implement relationships between objects, like many-to-one, etc ... at least not yet.  If you have a collection of custom typed objects, they will be persisted using Java's Object Serialization and the type must implement *java.io.Serializable*.

Also don't forget to implement equals and hashCode for your custom types.  Since they will be in a Collection, you will probably want this functionality to insure proper handling by the Collection.

(The implementation has been tested with Lists and Sets.)


.. _hom_custom_property_converters:

Custom Property Converters
==========================

In the above MyPojo example (listed in :ref:`Using the EntityManager <using_entity_manager>`) you may have noticed the type, Colors, which is an enumeration.  (It is in src/test/java/com/mycompany in the code base). :: 


	package com.real.hom;

	public enum Colors {
	    BLUE("Blue"),
	    RED("Red"),
	    GREEN("Green");

	    private final String name;

	    Colors(String name) {
	        this.name = name;
	    }

	    public String getName() {
	        return name;
	    }

	    public static Colors getInstance(String name) {
	        Colors[] tidArr = values();
	        for (Colors tid : tidArr) {
	            if (tid.getName().equals(name)) {
	                return tid;
	            }
	        }

	        throw new IllegalArgumentException("No Color with name, " + name);
	    }
	}


As you probably already know, Cassandra stores all column values as byte[].  So all POJO properties must be converted to a byte[] before sending to Cassandra server.  The EntityManager can only convert basic java types "out of the box".  However custom converters can be created and registered by using the @me.prettyprint.hom.annotations.Column annotation's "converter" property - note that the standard JPA @Column cannot be used.  (This technique is very similar to XStream's converter strategy.)  In MyPojo you can see the "colors" property is annotated like this::


	@me.prettyprint.hom.annotations.Column(name = "color", converter = ColorConverter.class)


This tells the EntityManager to use ColorConverter to convert to/from a byte[] for the "color" property in MyPojo::


	package com.real.hom;

	import com.real.hom.converters.Converter;

	public class ColorConverter implements Converter<Colors> {

	  @Override
	  public Colors convertCassTypeToObjType(Class<Colors> clazz, byte[] value) {
	    return Colors.getInstance(new String(value));
	  }

	  @Override
	  public byte[] convertObjTypeToCassType(Colors value) {
	    return value.getName().getBytes();
	  }
	}


When saving a POJO the ``ColorConverter.ConvertObjTypeToCassType`` is called.  When loading a POJO the ``ColorConverter.ConvertCassTypeToObjType`` is called.


.. _hom_annonymous_properties:

Anonymous Properties
====================

Anonymous properties are Cassandra columns that exist, but do not have an @Column in the POJO.  Here are some reasons they are required (and useful):

* Your POJO could have the notion of "optional" properties or "dynamic" properties
* Legacy columns in the ColumnFamily that must be preserved, but don't map directly to a POJO property

Using the MyPojo example (listed in  :ref:`Using the EntityManager <using_entity_manager>`), you can see::


	private Map<String, String> anonymousProps = new HashMap<String, String>();


This is how MyPojo chooses to store its anonymous properties, but it could just as easily be with a Set or any other way that fits the need.  The only requirement is that a Collection<Entry<String, String>> must be provided to the EntityManager when persisting the data.  There are two annotations on methods in the POJO::

	@AnonymousPropertyAddHandler
	public void addAnonymousProp(String name, String value) {
	    anonymousProps.put(name, value);
	}

	@AnonymousPropertyCollectionGetter
	public Collection<Entry<String, String>> getAnonymousProps() {
	    return anonymousProps.entrySet();
	}


| These two methods are how the EntityManager sets and gets the anonymous properties.  
| When saving a POJO the EntityManager will look for a method annotated with ``@AnonymousPropertyCollectionGetter``, and the POJO must return the Collection of properties. 
| When loading a POJO the EntityManager checks for a method annotated with ``@AnonymousPropertyAddHandler``.  
| If found, all columns will be retrieved from the ColumnFamily row and ones matching an ``@Column`` will be set in the POJO using its setter. 
| All others are handled by the method that has ``@AnonymousPropertyAddHandler``.  
| If no ``@AnonymousPropertyAddHandler`` is found, then only POJO properties annotated with ``@Column`` will be retrieved from Cassandra, and any others will remain in the row.



.. _hom_faq:

HOM FAQ
=======

If I annotate a base class with @Entity, will a derived class be persisted?
---------------------------------------------------------------------------

No, you must annotate the derived class with ``@Entity`` and ``@DiscriminatorValue``.

Does the object mapper perform any locking?
-----------------------------------------------

No, you must handle synchronization yourself using java synchronization techniques or distributed tools, like ZooKeeper or Hazelcast.





