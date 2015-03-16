package com.mdnet.travel.core.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.BaseDocument;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;

public class MyBaseDocument extends BaseDocument{
	protected Database db;
	
	public Database takeDbObj() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	@SuppressWarnings("rawtypes")
	public List<KeyValue> dataStatic (String methodName)
	{
		Options options = new Options();

		options.groupLevel(1);
		//options.descending(true);
		options.reduce(true);
		//options.limit(count);
		
		ViewResult<String> result =  db.queryView(methodName, String.class, options,
				null);
		List<KeyValue> kv = new ArrayList<KeyValue>();
		for(int i=0; i<result.getRows().size(); i++)
		{
			ValueRow<String> rrr = result.getRows().get(i);
			KeyValue a = new KeyValue();
			a.setKey((String) rrr.getKey());
			a.setValue(rrr.getValue());
			kv.add(a);
		}
		Collections.sort(kv);
		//Collections.reverse(kv);
		return kv;
	}
}
