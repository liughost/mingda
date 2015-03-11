package com.mdnet.travel.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jcouchdb.db.Database;
import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.mdnet.travel.core.dao.TourComments;
import com.mdnet.travel.core.dao.TourInfo;

@Service(CommentsService.SERVICE_NAME)
@Scope("singleton")
public class CommentsService extends BaseCouchService {
	public static final String SERVICE_NAME = "com.mdnet.travel.core.service.impl.CommentsService";

	public CommentsService() {
		if (db == null)
			db = new Database("guantravel.com", 5984, "comments");

	}

	public TourComments newComment() {
		return new TourComments(db);
	}

	public int test() {
		String mapFunc = "{\"map\":\"function(doc) {if(doc.tourKey == 'e2a0b070c8d540ea8b672114cd000ea7' ) emit(doc.tourKey, 1);}\",\"reduce\":\"function (key,value){return sum(value);}\"}";
		//List<TourComments> list = new ArrayList<TourComments>();
		Options options = new Options();

		options.descending(true);
		options.groupLevel(1);
		options.reduce(true);
		@SuppressWarnings("rawtypes")
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, options,
				null);	

		for (int i = 0; i < result.getRows().size(); i++) {
			System.out.println(result.getRows().get(0).getValue());
		}
		return 0;
	}

	public int commentsCount(String tourKey) {

		Options options = new Options();

		options.groupLevel(1);
		options.reduce(true);
		options.startKey(tourKey);

		ViewResult<String> result = db.queryView("static/count", String.class,
				options, null);

		int count = 0;
		if (result.getRows().size() > 0) {
			ValueRow<String> ret = result.getRows().get(0);
			ret.getId();
			ret.getKey();
			count = Integer.parseInt(ret.getValue());
		}
		return count;
	}

	/**
	 * function(doc) {emit(doc._id, doc);}
	 * 
	 * @param tourKey
	 * @param startKey
	 * @param count
	 * @return
	 */
	public List<TourComments> search(String tourKey, String startKey, int count) {
		String mapFunc = "{\"map\":\"function(doc) {if(doc.tourKey == '"
				+ tourKey + "' ) emit(doc._id, doc);}\"}";
		List<TourComments> list = new ArrayList<TourComments>();
		Options options = new Options();

		options.descending(true);
		options.limit(count);
		if (startKey != null && startKey.length() > 0)
			options.startKey(startKey);
		@SuppressWarnings("rawtypes")
		ViewResult<Map> result = db.queryAdHocView(Map.class, mapFunc, options,
				null);

		for (int i = 0; i < result.getRows().size(); i++) {
			TourComments tc = new TourComments();
			tc.Unseriable(result.getRows().get(i));
			// ti.seti
			list.add(tc);
		}
		return list;
	}

	public static void main(String[] args) {
		CommentsService cs = new CommentsService();
		cs.test();
		//cs.commentsCount("e2a0b070c8d540ea8b672114cd000ea7");
		// cs.search("e2a0b070c8d540ea8b672114cd000ea7", "", 1);
		// TourComments c = cs.newComment();
		// c.setCommentDate("2015-03-19 12:34:56");
		// c.setCommentIP("127.0.0.1");
		// c.setContext("评论评论");
		// c.setCustId(9);
		// c.setHeadImg("");
		// c.setNickName("la啦");
		// c.setTourKey("e2a0b070c8d540ea8b672114cd000ea7");
		// c.Add();
	}
}
