package wthack.reader;

import java.util.HashMap;
import java.util.Map;

import wthack.model.CareEntity;

public class ReaderContext {

	private Map<String, CareEntity> data;
	public CareEntity currentCountry = null;
	public CareEntity currentAuthority = null;
	public CareEntity currentTrust = null;
	public int offset = 0;
	
	public ReaderContext() {
		data = new HashMap<String, CareEntity>();
	}
	
	public Map<String, CareEntity> getData() {
		return data;
	}
	
	public void reset() {
		currentCountry = null;
		currentAuthority = null;
		currentTrust = null;
	}
	
	public void setCurrentCountry(CareEntity country) {
		currentCountry = country;
		currentAuthority = null;
		currentTrust = null;
	}
	
	public void setCurrentAuthority(CareEntity authority) {
		currentAuthority = authority;
		currentTrust = null;
	}
	
	public void setCurrentTrust(CareEntity trust) {
		currentTrust = trust;
	}
}
