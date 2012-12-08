package wthack;

import java.util.Map;

import wthack.model.CareEntity;
import wthack.reader.HesFileReader;
import wthack.writer.RiakWriter;

public class Main {

	public static void main(String[] args) {
		String fileName = null;
		String riakUrl = null;
		if(args.length < 1) {
			System.err.println("Usage: wthack.Main file [url]");
			System.exit(1);
		}
		fileName = args[0];
		if(args.length > 1) {
			riakUrl = args[1];
		}
		HesFileReader reader = new HesFileReader();
		RiakWriter writer = new RiakWriter(riakUrl);
		writer.clear();
		Map<String, CareEntity> data = reader.read(fileName);
		writer.write(data);
	}
}
