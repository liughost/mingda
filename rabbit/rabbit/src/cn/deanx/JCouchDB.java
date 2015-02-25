package cn.deanx;

import java.util.HashMap;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;

public class JCouchDB {
	protected Database db;

	public void connect() {
		db = new Database("deanx.cn", 5984, "mydatabase");
		
		System.out.println(db.getStatus());
	}

	public void add() {
		// create a hash map document with two fields
		Map<String, String> doc = new HashMap<String, String>();
		doc.put("foo", "value for the foo attribute");
		doc.put("bar", "value for the bar attribute");

		// create the document in couchdb
		db.createDocument(doc);

	}

	public void queryView() {
		Options opt = new Options();
		opt.limit(30);
		opt.reduce(false);
		//db.
		
		opt.descending(true);
		//opt.
		//opt.startKey("68f5888923e6a5829448e4bfce0023c9");
		ViewResult<Map> result = db.queryAdHocView(Map.class, "{\"map\":\"function(doc) {\n\n\tif(doc.foo != null && doc.foo == 'bar'){\n  emit(doc._id, doc);}\n}\"}", opt, null); 
				//db..queryView("test/test", Map.class, opt, null);
		//db.queryList(listName, viewName, options)
		//db.queryList(listName, viewName, options)
		for (ValueRow vr : result.getRows()) {
			System.out.println(vr.getId());
			
		}
		
	}

	public static void main(String[] args) {
		JCouchDB couch = new JCouchDB();
		couch.connect();
		// couch.add();
		couch.queryView();
	}
}
