.. highlight:: java

.. index:: DynamicComposite, composite, template

DynamicCompositeType with Templates
***********************************

To get a brief background see http://www.anuff.com/2010/07/secondary-indexes-in-cassandra.html. *DynamicCompositeType* lets you create column name or value using multiple columns. You can also do it using
String concatenation but it's just stored as *UTF8Type* or *ByteType*. Better way is to create sub columns with it's own comparators. *DynamicCompositeType* lets you do just that. You can do something like::

	c.addUTF8("john").addUTF8("smith").addLong(System.currentTimeMillis()).

Templates are easy representation of Hector API. It's easy to use and you like it once you start using it and start comparing your existing code with template code.

Create Column Family
====================

::

     create column family Composite with comparator ='DynamicCompositeType   
        (a=>AsciiType,b=>BytesType,i=>IntegerType,x=>LexicalUUIDType,l=>LongType,t=>TimeUUIDType,s=>UTF8Type,u=>UUIDType)' 
       	and default_validation_class=UTF8Type and key_validation_class=UTF8Type;

"a","b" are short notation which tells Thrift which comparatorType to use. You can use all the types as mentioned above or only the ones applicable to you.

Let's Code

Create Template Object
=======================

::

	private ColumnFamilyTemplate<String, DynamicComposite> compTemplate  =	
		         new ThriftColumnFamilyTemplate<String, DynamicComposite>(NoSQLDAOFactory.getInstance().getKeyspace(),
			"CompositeTest", StringSerializer.get(), new DynamicCompositeSerializer());

Mutations
=========

::

		ColumnFamilyUpdater<String, DynamicComposite> updater = compTemplate
				.createUpdater(entity.getKey()); //get Key and create updater object

		DynamicComposite ce = null;
		
		// For all the columns for your entity
		for (String col : entity.getCols().keySet()) {
			//create DynamicComposite object. In this case UTF8Type, UUIDType
			ce = new DynamicComposite(entity.getCols().get(col).getName(), NoSQLUtils.newTimeUUID()); 
			updater.setString(ce, entity
					.getCols().get(col).getValue());
		}
				ce = new DynamicComposite(ColumnNameType.ENTITY_TYPE.name());
				updater.setString(ce,
						EntityType.COMPOSITE.name());
		try {
			compTemplate.update(updater); //Do Final Update
			Log.info("Entity COMPOSITE " + entity.getKey());
		} catch (HectorException e) {
			e.printStackTrace();
			throw new DataSourceException(Errors.HECTOR_EXCEPTION);
		} finally {

		}

How does it look in the CLI
============================

::

     => (column=s@DOB:u@bed2e481-ba35-11e0-b239-005056c00008, value=123456789, timestamp=1311980081864001)
     => (column=s@ENTITY_TYPE, value=COMPOSITE, timestamp=1311980081864002)
     => (column=s@ENTITY_TYPE:u@bed2e480-ba35-11e0-b239-005056c00008, value=COMPOSITE, timestamp=1311980081864000)

Note how it prefixes with the types defined when column family was created.

Get Call
========

::

		try {
			ColumnFamilyResult<String, DynamicComposite> res = compTemplate
					.queryColumns(entity.getKey());
			if (null == res || !res.hasResults()) {
				throw new DataSourceException(Errors.NO_RESULT);
			}
			DynamicComposite ce  = new DynamicComposite(ColumnNameType.ENTITY_TYPE.name());
			String value = null;
			value = res.getString(ce);
			
			if (!EntityType.COMPOSITE.name().equalsIgnoreCase(value)){
				throw new DataSourceException(Errors.INALID_ENTITY_KEY);
			}
			
			entity.seteType(EntityType.COMPOSITE);
			String name = null;
			for (DynamicComposite dce : res.getColumnNames()) {
			       value = res.getString(dce);
			       name = dce.getComponent(0).toString();
			       Log.info("Composite Key " + name);
			       entity.getCols().put(name,new BigTableColumn<String>(name, value));
			}
		} catch (HectorException e) {
			// do something ...
			e.printStackTrace();
			throw new DataSourceException(Errors.HECTOR_EXCEPTION);
		} finally {

		}

``.getComponent(index)`` gets the sub column that was created in mutation step.

**That's it!!**

If you are not using templates and want to use them with Hector's mutators and Column Slice directly see https://github.com/edanuff/CassandraIndexedCollections. This will help you in understanding how it actually works.