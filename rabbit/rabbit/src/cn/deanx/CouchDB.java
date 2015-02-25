package cn.deanx;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.fourspaces.couchdb.AdHocView;
import com.fourspaces.couchdb.CouchResponse;
import com.fourspaces.couchdb.Database;
import com.fourspaces.couchdb.Document;
import com.fourspaces.couchdb.Session;
import com.fourspaces.couchdb.View;
import com.fourspaces.couchdb.ViewResults;

public class CouchDB {
	// public getRESTClient(){
	// return new RESTClient("http://localhost:5498/")
	// }
	Session session;

	Database database = null;

	public void connect() {
		this.session = new Session("deanx.cn", 5984);

		this.database = session.getDatabase("mydatabase");
	}

	public void add() {
		Document d = new Document();
		d.put("foo", "汉字常常阿斯科利的放假啦快递费");
		database.saveDocument(d);

		// Document doc = database.getDocument("documentid1234");
		// doc.put("foo","bar");
		// database.save(doc);

		// Document newdoc = new Document();
		// newdoc.put("foo","baz333");
		// database.saveDocument(newdoc); // auto-generated id given by the
		// database
	}

	public ViewResults getall() {
		return database.getAllDocuments();

	}

	public void listAll(ViewResults vr) {

		// System.out.println(vr.toString());
		JSONArray jsonData = (JSONArray) vr.get("rows");
		for (Object object : jsonData) {
			JSONObject jsonObject = (JSONObject) object;
			// JSONObject row =
			// database.getDocument("e0c6ee6f06971ecd92d0610892002958").getJSONObject();
			System.out.println(jsonObject.get("id"));
		}
	}

	public void addHoc() {
		// AdHocView av = new AdHocView(null);
		ViewResults vr = database.adhoc(// "function(doc) { emit(doc._id, doc);}");
				"{\"language\":\"javascript\",\"map\":\"function(doc) {\n\n\tif(doc.foo != null && doc.foo == 'bar'){\n  emit(doc._id, doc);}\n}\"}");
		List<Document> documents = vr.getResults();

		for (Document document : documents) {
			String id = document.getString("id");
			JSONObject row = database.getDocument(id).getJSONObject();
			System.out.println(row.get("foo"));
		}
	}

	public void visitView(String viewPath) {
		View v = new View(viewPath);
	//	v.getQueryString()
		//v.setSkip("68f5888923e6a5829448e4bfce0006f6");//.setSkip(skip).setCount(2);
		//CouchResponse r = new CouchResponse(HttpMethod.GET);
		
		
		ViewResults vr = this.database.view(v);
		this.listAll(vr);
	}

	public static void main(String[] args) {
		CouchDB couch = new CouchDB();
		couch.connect();

		// couch.add();

		//couch.listAll(couch.getall() );

		// couch.addHoc();

		//couch.visitView("_design/test/_view/test");
		couch.visitView("_design/all/_view/all");
	}
}
