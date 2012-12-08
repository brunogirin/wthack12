package wthack.writer;

import java.util.Map;

import wthack.model.CareEntity;

import com.basho.riak.client.IRiakClient;
import com.basho.riak.client.RiakException;
import com.basho.riak.client.RiakFactory;
import com.basho.riak.client.bucket.Bucket;

public class RiakWriter {
	
	public static final String BUCKET_NAME = "maternity";

	private String url;
	
	public RiakWriter(String url) {
		this.url = (url==null || url.isEmpty())?"http://localhost:8091/riak":url;
	}
	
	public void clear() {
		System.out.println("Cleaning data in Riak instance "+this.url);
		try {
			IRiakClient riakClient = RiakFactory.httpClient(this.url);
			Bucket bucket = riakClient.fetchBucket(BUCKET_NAME).execute();
			for(String key : bucket.keys()) {
				bucket.delete(key).execute();
			}
			riakClient.shutdown();
		} catch (RiakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void write(Map<String, CareEntity> data) {
		System.out.println("Writing data to Riak instance "+this.url);
		try {
			IRiakClient riakClient = RiakFactory.httpClient(this.url);
			Bucket bucket = riakClient.fetchBucket(BUCKET_NAME).execute();
			for(Map.Entry<String, CareEntity> entry : data.entrySet()) {
				bucket.store(entry.getKey(), entry.getValue()).execute();
			}
			riakClient.shutdown();
		} catch (RiakException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
