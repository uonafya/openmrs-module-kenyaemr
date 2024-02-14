/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.builder.common;

import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH705.MOH705IndicatorLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Report builder for MOH705B
 * Equal and above 5 years
 * Diagnosis
 */
@Component
@Builds({ "kenyaemr.common.report.moh705B" })
public class MOH705BReportBuilder extends AbstractReportBuilder {
	static final String EQUAL_AND_OVER_FIVE = ">=5";
	static final int DIARRHOEA = 5018;
	static final int TUBERCULOSIS = 160156;
	static final int DYSENTRY = 152 ;
	static final int CHOLERA = 145622;
	static final int MENINGOCOCCAL_MENINGITIS = 134369;
	static final int OTHER_MENINGITIS = 0;
	static final int TETANUS = 124957;
	static final int POLIOMYELITIS = 5258;
	static final int CHICKEN_POX = 892;
	static final int MEASLES = 134561;
	static final int HEPATITIS = 116986;
	static final int MUMPS = 133671;
	static final int SUSPECTED_MALARIA = 166623 ;
	static final int CONFIRMED_MALARIA = 160148;
	static final int TESTED_FOR_MALARIA = 168740;
	static final int MALARIA_IN_PREGNANCY = 160152;
	static final int AMOEBIASIS = 124;
	static final int TYPHOID_FEVER = 141;
	static final int SEXUALLY_TRANSMITTED_INFECTIONS = 112992;
	static final int URINARY_TRACT_INFECTIONS = 111633;
	static final int BILHARZIA = 117152;
	static final int INTESTINAL_WORMS = 116699;
	static final int MALNUTRITION = 134725;
	static final int ANAEMIA = 121629;
	static final int EYE_INFECTIONS = 140832;
	static final int EAR_INFECTIONS = 71;
	static final int UPPER_RESPIRATORY_TRACT_INFECTIONS = 123093;
	static final int ASTHMA = 121375;
	static final int PNEUMONIA = 114100;
	static final int OTHER_LOWER_RESPIRATORY_TRACT_INFECTIONS = 998;
	static final int ABORTION = 0;
	static final int PUERPERIUM_AT_CHILDBIRTH = 0;
	static final int HYPERTENSION = 117399;
	static final int MENTAL_DISORDERS = 77;
	static final int DENTAL_DISORDERS = 78;
	static final int JIGGERS_INFESTATION = 123964;
	static final int SKIN_DISEASES = 119022;
	static final int ARTHRITIS_JOINT_PAINS = 148432;
	static final int POISONING = 114088;
	static final int ROAD_TRAFFIC_INJURIES = 86;
	static final int DEATH_BY_ROAD_TRAFFIC_INJURIES = 0;
	static final int OTHER_INJURIES = 0;
	static final int SEXUAL_VIOLENCE = 123160;
	static final int VIOLENCE_RELATED_INJURIES = 0;
	static final int BURNS = 146623;
	static final int SNAKE_BITES = 126323;
	static final int DOG_BITES = 166;
	static final int OTHER_BITES = 0;
	static final int DIABETES = 119481;
	static final int EPILEPSY = 155;
	static final int BRUCELLOSIS = 121005;
	static final int CARDIOVASCULAR_CONDITIONS = 119270;
	static final int CENTRAL_NERVOUS_SYSTEM_CONDITIONS = 118994;
	static final int OVERWEIGHT = 114413;
	static final int MUSCULAR_SKELETAL_CONDITIONS = 0;
	static final int FISTULA_BIRTH_RELATED = 0;
	static final int SUSPECTED_NEOPLAMS_CANCERS = 0;
	static final int PHYSICAL_DISABILITY = 0;
	static final int TRYPONOSOMIASIS = 124078;
	static final int RIFT_VALLEY_FEVER = 113217;
	static final int YELLOW_FEVER = 122759;
	static final int VIRAL_HAEMORRHAGIC_FEVER = 123112;
	static final int CHIKUNGUNYA = 120743;
	static final int DENGUE_FEVER = 7592;
	static final int LEISHMANIASIS = 116350;
	static final int CUTANEOUS_LEISHMANIASIS = 143074;
	static final int SUSPECTED_ANTHRAX = 168741;


	@Autowired
	private MOH705IndicatorLibrary moh705indicatorLibrary;

	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("dateBasedReporting", "", String.class));
	}

	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
	        ReportDefinition reportDefinition) {
		return Arrays.asList(ReportUtils.map(moh705BDataset(), "startDate=${startDate},endDate=${endDate}"));
	}

	protected DataSetDefinition moh705BDataset() {
		CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
		cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cohortDsd.setName("MOH705B");
		cohortDsd.setDescription("MOH 705B");

		String indParams = "startDate=${startDate},endDate=${endDate}";

		cohortDsd.addColumn("Diarrhoea", "",
		    ReportUtils.map(moh705indicatorLibrary.diagnosis(DIARRHOEA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Tuberculosis", "",
		    ReportUtils.map(moh705indicatorLibrary.diagnosis(TUBERCULOSIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Dysentry", "",
		    ReportUtils.map(moh705indicatorLibrary.diagnosis(DYSENTRY,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Cholera", "",
		    ReportUtils.map(moh705indicatorLibrary.diagnosis(CHOLERA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Meningococcal Meningitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MENINGOCOCCAL_MENINGITIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Other Meningitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OTHER_MENINGITIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Tetanus", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(TETANUS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Poliomyelitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(POLIOMYELITIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Chicken Pox", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CHICKEN_POX,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Measles", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MEASLES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Hepatitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(HEPATITIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Mumps", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MUMPS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Suspected Malaria", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SUSPECTED_MALARIA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Tested for Malaria", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(TESTED_FOR_MALARIA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Confirmed Malaria", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CONFIRMED_MALARIA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Malaria in pregnancy", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MALARIA_IN_PREGNANCY,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Amoebiasis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(AMOEBIASIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Typhoid fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(TYPHOID_FEVER,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Sexually Transmitted Infections", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SEXUALLY_TRANSMITTED_INFECTIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Urinary Tract Infections", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(URINARY_TRACT_INFECTIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Bilharzia", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(BILHARZIA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Intestinal worms", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(INTESTINAL_WORMS, EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Malnutrition", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MALNUTRITION,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Anaemia", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(ANAEMIA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Eye Infections or Conditions", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(EYE_INFECTIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Ear Infections or Conditions", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(EAR_INFECTIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Upper Respiratory Tract Infections", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(UPPER_RESPIRATORY_TRACT_INFECTIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Asthma", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(ASTHMA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Pneumonia", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(PNEUMONIA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Other Lower Respiratory Tract Infections", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OTHER_LOWER_RESPIRATORY_TRACT_INFECTIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Abortion", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(ABORTION,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Puerperium at Childbirth", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(PUERPERIUM_AT_CHILDBIRTH,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Hypertension", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(HYPERTENSION,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Mental Disorders", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MENTAL_DISORDERS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Dental Disorders", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DENTAL_DISORDERS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Jiggers Infestation", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(JIGGERS_INFESTATION,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Diseases of the skin", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SKIN_DISEASES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Arthritis and Joint pains", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(ARTHRITIS_JOINT_PAINS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Poisoning", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(POISONING,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Road Traffic Injuries", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(ROAD_TRAFFIC_INJURIES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Deaths due to Road Traffic Injuries", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DEATH_BY_ROAD_TRAFFIC_INJURIES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Violence related injuries", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(VIOLENCE_RELATED_INJURIES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Other injuries", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OTHER_INJURIES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Sexual Violence", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SEXUAL_VIOLENCE,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Burns", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(BURNS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Snake Bites", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SNAKE_BITES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Dog Bites", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DOG_BITES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Other Bites", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OTHER_BITES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Diabetes", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DIABETES,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Epilepsy", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(EPILEPSY,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Brucellosis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(BRUCELLOSIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Cardiovascular conditions", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CARDIOVASCULAR_CONDITIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Central Nervous System Conditions", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CENTRAL_NERVOUS_SYSTEM_CONDITIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Overweight", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OVERWEIGHT,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Muscular skeletal conditions", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MUSCULAR_SKELETAL_CONDITIONS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Fistula related with Birth", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(FISTULA_BIRTH_RELATED,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Suspected Neoplams and Cancers", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SUSPECTED_NEOPLAMS_CANCERS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Physical Disability", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(PHYSICAL_DISABILITY,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Trypanosomiasis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(TRYPONOSOMIASIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Yellow Fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(YELLOW_FEVER,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Viral Haemorrhagic Fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(VIRAL_HAEMORRHAGIC_FEVER,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Rift valley fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(RIFT_VALLEY_FEVER,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Chikungunya", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CHIKUNGUNYA,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Dengue fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DENGUE_FEVER,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Leishmaniasis or Kalaazar", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(LEISHMANIASIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Cutaneous leishmaniasis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CUTANEOUS_LEISHMANIASIS,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Suspected Anthrax", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SUSPECTED_ANTHRAX,EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("All Other Diseases", "",
				ReportUtils.map(moh705indicatorLibrary.allOtherDiseasesAboveFive(EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("New Attendances", "",
				ReportUtils.map(moh705indicatorLibrary.newAttendances(EQUAL_AND_OVER_FIVE), indParams), "");
		cohortDsd.addColumn("Re Attendances", "",
				ReportUtils.map(moh705indicatorLibrary.reAttendances(EQUAL_AND_OVER_FIVE), indParams), "");
		return cohortDsd;
	}
}
