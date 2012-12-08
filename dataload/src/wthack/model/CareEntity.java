package wthack.model;

import java.util.HashMap;
import java.util.Map;

public final class CareEntity {

	public String code;
	public String name;
	public CareEntityType type;
	public String parentCode;
	public Map<String, MaternityStatistics> statistics = new HashMap<>();
}
