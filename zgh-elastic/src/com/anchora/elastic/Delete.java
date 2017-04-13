package com.anchora.elastic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONObject;

import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

public class Delete {

	public void deleteBydate(String index, String type, String id) {

		TransportClient client = ElasticsearchUtil.getClient();
		DeleteRequestBuilder drBuilder = client.prepareDelete(index, type, id);

		// DeleteRequestBuilder drBuilder =
		// client.prepareDeleteIndexedScript(scriptLang, id)

		DeleteResponse response = drBuilder.execute().actionGet();
		if (response.isFound())
			System.out.println("");
		else
			System.out.println("");
	}

	// public List<String> searchBydate(String index,Date date){
	// TransportClient client = ElasticsearchUtil.getClient();
	// List<String> hitcontents = new ArrayList<String>();
	// JSONObject obj = null;
	//
	// SearchRequestBuilder sbuilder = client.prepareSearch(index);
	//
	// SearchResponse response = sbuilder.execute().actionGet();
	// SearchHits hits = response.getHits();
	// // System.out.println(response.get);
	//
	// SearchHit[] h = hits.getHits();
	// for (SearchHit hit : h) {
	// // System.out.println(hit.getId());
	// obj = JSONObject.fromObject(hit.getSourceAsString());
	// // System.out.println(hit.getSourceAsString());
	// Date newdate = dateutil(obj.getString("@timestamp"));
	//
	// if (newdate.before(date))
	// hitcontents.add(hit.getSourceAsString());
	// }
	// return hitcontents;
	// }

	public List<String> searchforId(String index, Date date) {

		TransportClient client = ElasticsearchUtil.getClient();
		List<String> hitid = new ArrayList<String>();
		JSONObject obj = null;

		SearchRequestBuilder sbuilder = client.prepareSearch(index);

		SearchResponse response = sbuilder.execute().actionGet();
		SearchHits hits = response.getHits();
		// System.out.println(response.get);

		SearchHit[] h = hits.getHits();
		for (SearchHit hit : h) {
			// System.out.println(hit.getId());
			obj = JSONObject.fromObject(hit.getSourceAsString());
			// System.out.println(hit.getSourceAsString());
			Date newdate = dateutil(obj.getString("@timestamp"));

			if (newdate.before(date))
				// hitcontents.add(hit.getSourceAsString());
				hitid.add(hit.getId());
		}
		return hitid;
	}

	private Date dateutil(String date) {
		date = date.replace("Z", " UTC");//
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd'T'HH:mm:ss.SSS Z");// 
		Date d = null;
		try {
			d = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;

	}

	public static void delete(String index) {
		Delete delete = new Delete();

		List<String> hitid = new ArrayList<String>();

		Date d = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String strdate = df.format(d);
		Date date;
		try {
			date = df.parse(strdate);
			hitid = delete.searchforId(index, date);

			Iterator<String> itid = hitid.iterator();

			while (itid.hasNext()) {
				System.out.println(itid.next().toString());
				delete.deleteBydate(index, "syslog", itid.next().toString());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		
	}

}
