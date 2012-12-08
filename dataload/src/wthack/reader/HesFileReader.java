package wthack.reader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import wthack.model.CareEntity;
import wthack.model.CareEntityType;
import wthack.model.MaternityStatistics;

public class HesFileReader {

	private Pattern mpdpSheetNamePattern = Pattern.compile(
			"MPDP Flat [fF]ile \\(([0-1][0-9]-[0-1][0-9])\\)");
	private Pattern codeCountryPattern = Pattern.compile("ALL");
	private Pattern codeAuthorityPattern = Pattern.compile("Q[0-9]{2}");
	private Pattern codeTrustPattern = Pattern.compile("[A-Z0-9]{3}(-X)?");
	private Pattern codeHospitalPattern = Pattern.compile("[A-Z0-9]{5}");
	
	public HesFileReader() {
		
	}
	
	public Map<String, CareEntity> read(String fileName) {
		ReaderContext context = new ReaderContext();
		//
		InputStream inp = null;
		try {
			inp = new FileInputStream(fileName);
			Workbook wb = WorkbookFactory.create(inp);
			int numSheets = wb.getNumberOfSheets();
			for(int i = 0; i < numSheets; i++) {
				String sheetName = wb.getSheetName(i);
				Matcher m = mpdpSheetNamePattern.matcher(sheetName);
				if(m.matches() && m.groupCount() > 0) {
					String key = m.group(1);
					System.out.println("Processing sheet "+sheetName+" with key "+key);
					Sheet sheet = wb.getSheetAt(i);
					readSheet(context, key, sheet);
				} else {
					System.out.println("Skipping sheet "+sheetName);
				}
			}
		} catch (InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(inp != null) {
				try {
					inp.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//
		return context.getData();
	}
	
	private void readSheet(ReaderContext context, String key, Sheet sheet) {
		if("11-12".equals(key)) {
			context.offset = context.offset = 2;
		} else {
			context.offset = 0;
		}
		for(Row row : sheet) {
			if(row != null) {
				Cell codeCell = row.getCell(context.offset+0);
				Cell nameCell = row.getCell(context.offset+1);
				if(
						codeCell != null &&
						codeCell.getCellType() == Cell.CELL_TYPE_STRING &&
						nameCell != null &&
						nameCell.getCellType() == Cell.CELL_TYPE_STRING
						) {
					String code = codeCell.getRichStringCellValue().getString().trim();
					String name = nameCell.getRichStringCellValue().getString().trim();
					if(!code.isEmpty()) {
						CareEntity entity = getOrCreateEntity(context, code, name);
						readRow(context, key, row, entity);
					}
				}
			}
		}
	}
	
	private void readRow(ReaderContext context, String key, Row row, CareEntity entity) {
		MaternityStatistics stats = getOrCreateStatistics(entity, key);
		if(stats != null) {
			// Table A
			stats.gestationFirstAssessment.p0_4w = readIntCell(context, row, 2);
			stats.gestationFirstAssessment.p5_9w = readIntCell(context, row, 3);
			stats.gestationFirstAssessment.p10_14w = readIntCell(context, row, 4);
			stats.gestationFirstAssessment.p10w = readIntCell(context, row, 5);
			stats.gestationFirstAssessment.p11w = readIntCell(context, row, 6);
			stats.gestationFirstAssessment.p12w = readIntCell(context, row, 7);
			stats.gestationFirstAssessment.p13w = readIntCell(context, row, 8);
			stats.gestationFirstAssessment.p14w = readIntCell(context, row, 9);
			stats.gestationFirstAssessment.p15_19w = readIntCell(context, row, 10);
			stats.gestationFirstAssessment.p20_24w = readIntCell(context, row, 11);
			stats.gestationFirstAssessment.p25_29w = readIntCell(context, row, 12);
			stats.gestationFirstAssessment.p30_34w = readIntCell(context, row, 13);
			stats.gestationFirstAssessment.p35_39w = readIntCell(context, row, 14);
			stats.gestationFirstAssessment.p40pw = readIntCell(context, row, 15);
			stats.gestationFirstAssessment.pUnknown = readIntCell(context, row, 16);
			// Table B
			stats.gestationTerm.p22w = readIntCell(context, row, 17);
			stats.gestationTerm.p23_25w = readIntCell(context, row, 18);
			stats.gestationTerm.p26_28w = readIntCell(context, row, 19);
			stats.gestationTerm.p29_31w = readIntCell(context, row, 20);
			stats.gestationTerm.p32_34w = readIntCell(context, row, 21);
			stats.gestationTerm.p35_37w = readIntCell(context, row, 22);
			stats.gestationTerm.p38_40w = readIntCell(context, row, 23);
			stats.gestationTerm.p41_43w = readIntCell(context, row, 24);
			stats.gestationTerm.p44pw = readIntCell(context, row, 25);
			stats.gestationTerm.pUnknown = readIntCell(context, row, 26);
			// Table C
			stats.onset.spontaneous = readIntCell(context, row, 27);
			stats.onset.caesarean = readIntCell(context, row, 28);
			stats.onset.surgicalInduction = readIntCell(context, row, 29);
			stats.onset.medicalInduction = readIntCell(context, row, 30);
			stats.onset.surgicalAndMedicalInduction = readIntCell(context, row, 31);
			stats.onset.unknown = readIntCell(context, row, 32);
			// Table D
			stats.delivery.caesarean.elective = readIntCell(context, row, 36);
			stats.delivery.caesarean.emergency = readIntCell(context, row, 37);
			stats.delivery.instrumental.breechExtraction = readIntCell(context, row, 38);
			stats.delivery.instrumental.forcepsLow = readIntCell(context, row, 39);
			stats.delivery.instrumental.forcepsOther = readIntCell(context, row, 40);
			stats.delivery.instrumental.ventouse = readIntCell(context, row, 41);
			stats.delivery.spontaneous.breech = readIntCell(context, row, 42);
			stats.delivery.spontaneous.vertex = readIntCell(context, row, 43);
			stats.delivery.spontaneous.other = readIntCell(context, row, 44);
			stats.delivery.other = readIntCell(context, row, 45);
			stats.delivery.unknown = readIntCell(context, row, 46);
			// Table E
			stats.person.doctor = readIntCell(context, row, 47);
			stats.person.midwife = readIntCell(context, row, 48);
			stats.person.other = readIntCell(context, row, 49);
			stats.person.unknown = readIntCell(context, row, 50);
			// Table F
			stats.place.consultantWard = readIntCell(context, row, 51);
			stats.place.gpWard = readIntCell(context, row, 52);
			stats.place.consultantMidwifeGpWard = readIntCell(context, row, 53);
			stats.place.midwifeOtherWard = readIntCell(context, row, 54);
			stats.place.unknwon = readIntCell(context, row, 55);
			// Table G
			stats.selectedStats.spontaneousWithEpisiotomy = readIntCell(context, row, 56);
			stats.selectedStats.caesareanWithPostnatalStay = readIntCell(context, row, 58);
			stats.selectedStats.caesareanAnaestetic.general = readIntCell(context, row, 60);
			stats.selectedStats.caesareanAnaestetic.spinal = readIntCell(context, row, 62);
			stats.selectedStats.caesareanAnaestetic.epidural = readIntCell(context, row, 64);
			stats.selectedStats.caesareanAnaestetic.other = readIntCell(context, row, 66);
			stats.selectedStats.caesareanAnaestetic.unknown = readIntCell(context, row, 68);
			// Table H
			stats.unassistedDeliveries.onsetSpontaneous = readIntCell(context, row, 71);
			stats.unassistedDeliveries.methodSpontaneous = readIntCell(context, row, 72);
			stats.unassistedDeliveries.withoutEpisiotomy = readIntCell(context, row, 73);
			stats.unassistedDeliveries.unassisted = readIntCell(context, row, 74);
			//
			stats.total = readIntCell(context, row, 76);
		}
	}
	
	private int readIntCell(ReaderContext context, Row row, int cellIndex) {
		Cell cell = row.getCell(context.offset + cellIndex);
		int value = 0;
		if(cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = (int)Math.floor(cell.getNumericCellValue());
		}
		return value;
	}
	
	private CareEntity getOrCreateEntity(ReaderContext context, String code, String name) {
		CareEntity entity = context.getData().get(code);
		if(entity == null) {
			entity = new CareEntity();
			if(codeCountryPattern.matcher(code).matches()) {
				entity.type = CareEntityType.COUNTRY;
				context.setCurrentCountry(entity);
			} else if(codeAuthorityPattern.matcher(code).matches()) {
				entity.type = CareEntityType.AUTHORITY;
				entity.parentCode = context.currentCountry.code;
				context.setCurrentAuthority(entity);
			} else if(codeTrustPattern.matcher(code).matches()) {
				entity.type = CareEntityType.TRUST;
				entity.parentCode = context.currentAuthority.code;
				context.setCurrentTrust(entity);
			} else if(codeHospitalPattern.matcher(code).matches()) {
				entity.type = CareEntityType.HOSPITAL;
				entity.parentCode = context.currentTrust.code;
			} else {
				entity = null;
				System.err.println("Unrecognised code: "+code);
			}
			if(entity != null) {
				entity.code = code;
				entity.name = name;
				context.getData().put(code, entity);
			}
		}
		return entity;
	}
	
	private MaternityStatistics getOrCreateStatistics(CareEntity entity, String key) {
		if(entity != null && key != null) {
			MaternityStatistics stats = entity.statistics.get(key);
			if(stats == null) {
				stats = new MaternityStatistics();
				entity.statistics.put(key, stats);
			}
			return stats;
		} else {
			return null;
		}
	}
}
