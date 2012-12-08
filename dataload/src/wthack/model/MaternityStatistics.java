package wthack.model;

public final class MaternityStatistics {
	
	public class GestationFirstAssessment {
		public int p0_4w;
		public int p5_9w;
		public int p10_14w;
		public int p10w;
		public int p11w;
		public int p12w;
		public int p13w;
		public int p14w;
		public int p15_19w;
		public int p20_24w;
		public int p25_29w;
		public int p30_34w;
		public int p35_39w;
		public int p40pw;
		public int pUnknown;
	}
	
	public class GestationTerm {
		public int p22w;
		public int p23_25w;
		public int p26_28w;
		public int p29_31w;
		public int p32_34w;
		public int p35_37w;
		public int p38_40w;
		public int p41_43w;
		public int p44pw;
		public int pUnknown;
	}
	
	public class OnsetMethod {
		public int spontaneous;
		public int caesarean;
		public int surgicalInduction;
		public int medicalInduction;
		public int surgicalAndMedicalInduction;
		public int unknown;
	}
	
	public class DeliveryMethod {
		public class Caesarean {
			public int elective;
			public int emergency;
		}
		public class Instrumental {
			public int breechExtraction;
			public int forcepsLow;
			public int forcepsOther;
			public int ventouse;
		}
		public class Spontaneous {
			public int breech;
			public int vertex;
			public int other;
		}
		public Caesarean caesarean = new Caesarean();
		public Instrumental instrumental = new Instrumental();
		public Spontaneous spontaneous = new Spontaneous();
		public int other;
		public int unknown;
	}
	
	public class Person {
		public int doctor;
		public int midwife;
		public int other;
		public int unknown;
	}
	
	public class Place {
		public int consultantWard;
		public int gpWard;
		public int consultantMidwifeGpWard;
		public int midwifeOtherWard;
		public int unknwon;
	}
	
	public class SelectedStats {
		public class CaesareanAnaestetic {
			public int general;
			public int spinal;
			public int epidural;
			public int other;
			public int unknown;
		}
		
		public int spontaneousWithEpisiotomy;
		public int caesareanWithPostnatalStay;
		public CaesareanAnaestetic caesareanAnaestetic = new CaesareanAnaestetic();
	}
	
	public class UnassistedDeliveries {
		public int onsetSpontaneous;
		public int methodSpontaneous;
		public int withoutEpisiotomy;
		public int unassisted;
	}
	
	public GestationFirstAssessment gestationFirstAssessment = new GestationFirstAssessment();
	public GestationTerm gestationTerm = new GestationTerm();
	public OnsetMethod onset = new OnsetMethod();
	public DeliveryMethod delivery = new DeliveryMethod();
	public Person person = new Person();
	public Place place = new Place();
	public SelectedStats selectedStats = new SelectedStats();
	public UnassistedDeliveries unassistedDeliveries = new UnassistedDeliveries();
	public int total;
}
