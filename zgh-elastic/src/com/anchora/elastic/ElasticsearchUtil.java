package com.anchora.elastic;

import java.net.InetAddress;

//import org.apache.log4j.Logger;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ElasticsearchUtil {

//	private static final Logger LOGGER = Logger
//			.getLogger(ElasticsearchUtil.class);

	public static TransportClient getClient() {
		TransportClient client = null;
		try {
			client = TransportClient
					.builder()
					.build()
					.addTransportAddress(
							new InetSocketTransportAddress(InetAddress
									.getByName("10.0.0.30"), 9300));
			return client;
		} catch (Exception e) {
//			LOGGER.info("link elasticsearch failed !");

		}
		return client;
	}

	public static void close(TransportClient client) {
		if (client != null)
			client.close();
	}

}
