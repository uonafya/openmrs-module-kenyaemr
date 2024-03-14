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
import org.openmrs.module.kenyaemr.reporting.EmrReportingUtils;
import org.openmrs.module.kenyaemr.reporting.Moh705ReportUtils.Moh705ReportDimension;
import org.openmrs.module.kenyaemr.reporting.Moh705ReportUtils.ReportAddonUtils;
import org.openmrs.module.kenyaemr.reporting.Moh705ReportUtils.ReportingUtils;
import org.openmrs.module.kenyaemr.reporting.Moh705ReportUtils.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH705.MOH705IndicatorLibrary;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Report builder for MOH705A
 * Under 5 years
 * Diagnosis
 */
@Component
@Builds({ "kenyaemr.common.report.moh705A" })
public class MOH705AReportBuilder extends AbstractReportBuilder {
	static final String AGE_BELOW_FIVE = "<5";
	static final int DIARRHOEA_WITH_DEHYDRATION = 168737;
	static final int DIARRHOEA_WITH_SOME_DEHYDRATION = 166569;
	static final int DIARRHOEA_WITH_SEVERE_DEHYDRATION = 168736 ;
	static final int CHOLERA = 145622;
	static final int DYSENTRY = 152 ;
	static final int GASTROENTERITIS = 117889;
	static final int PNEUMONIA = 114100;
	static final int SEVERE_PNEUMONIA = 168738;
	static final int UPPER_RESPIRATORY_TRACT_INFECTIONS = 123093;
	static final int LOWER_RESPIRATORY_TRACT_INFECTIONS = 135556;
	static final int ASTHMA = 121375;
	static final int PRESUMED_TUBERCULOSIS = 168739;
	static final int SUSPECTED_MALARIA = 166623 ;
	static final int TESTED_FOR_MALARIA = 168740;
	static final int CONFIRMED_MALARIA = 160148;
	static final int EAR_INFECTION = 71;
	static final int MALNUTRITION = 115122;
	static final int ANAEMIA = 121629;
	static final int MENINGOCOCCAL_MENINGITIS = 134369;
	static final int OTHER_MENINGITIS = 0;
	static final int NEONATAL_SEPSIS = 226;
	static final int NEONATAL_TETANUS = 124954;
	static final int POLIOMYELITIS = 160426;
	static final int CHICKEN_POX = 892;
	static final int MEASLES = 134561;
	static final int HEPATITIS = 116986;
	static final int AMOEBIASIS = 124;
	static final int MUMPS = 133671;
	static final int TYPHOID_FEVER = 141;
	static final int BILHARZIA = 117152;
	static final int INTESTINAL_WORMS = 116699;
	static final int EYE_INFECTIONS = 140832;
	static final int TONSILITIS = 112;
	static final int URINARY_TRACT_INFECTIONS = 111633;
	static final int MENTAL_DISORDERS = 77;
	static final int DENTAL_DISORDERS = 78;
	static final int JIGGERS_INFESTATION = 123964;
	static final int SKIN_DISEASES = 119022;
	static final int DOWNS_SYNDROME = 144481;
	static final int POISONING = 114088;
	static final int ROAD_TRAFFIC_INJURIES = 86;
	static final int DEATH_BY_ROAD_TRAFFIC_INJURIES = 0;
	static final int VIOLENCE_RELATED_INJURIES = 0;
	static final int OTHER_INJURIES = 0;
	static final int SEXUAL_VIOLENCE = 123160;
	static final int BURNS = 146623;
	static final int SNAKE_BITES = 126323;
	static final int DOG_BITES = 166;
	static final int OTHER_BITES = 0;
	static final int DIABETES = 119481;
	static final int EPILEPSY = 155;
	static final int OTHER_CONVULSIVE_DISORDERS = 0;
	static final int RHEUMATIC_FEVER = 127447;
	static final int BRUCELLOSIS = 121005;
	static final int RICKETS = 127394;
	static final int CEREBRAL_PALSY = 152492;
	static final int AUTISM = 121303;
	static final int TRYPONOSOMIASIS = 124078;
	static final int YELLOW_FEVER = 122759;
	static final int VIRAL_HAEMORRHAGIC_FEVER = 123112;
	static final int RIFT_VALLEY_FEVER = 113217;
	static final int CHIKUNGUNYA = 120743;
	static final int DENGUE_FEVER = 7592;
	static final int LEISHMANIASIS = 116350;
	static final int CUTANEOUS_LEISHMANIASIS = 143074;
	static final int SUSPECTED_ANTHRAX = 168741;
	static final int SUSPECTED_CHILDHOOD_CANCERS = 0;
	static final int HYPOXAEMIA = 117312;
	static final int ALL_OTHER_DISEASES = 0;

	
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
		return Arrays.asList(ReportUtils.map(moh705ADataset(), "startDate=${startDate},endDate=${endDate}"));
	}

	protected DataSetDefinition moh705ADataset() {
		String indParams = "startDate=${startDate},endDate=${endDate}";

		CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
		cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cohortDsd.setName("MOH705A");
		cohortDsd.setDescription("MOH 705A");			
//		cohortDsd.addDimension("day",		


		// populate datasets
//		EmrReportingUtils.addRow(indicatorDsd,"HV02-01", "First ANC Visit", ReportUtils.map(moh731GreenCardIndicators.firstANCVisitMchmsAntenatal(), indParams), cadreDisaggregation,Arrays.asList("1","2","3"));
		ReportingUtils.addRow(cohortDsd,"DWND","Diarrhoea with no dehydration",ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(DIARRHOEA_WITH_DEHYDRATION),AGE_BELOW_FIVE),indParams),ReportAddonUtils.getAdultChildrenWithGenderColumns());
//		System.out.println("Cohort indicator dataset def ==>"+cohortDsd);
		
		
		cohortDsd.addColumn("Diarrhoea with no dehydration", "",
		    ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(DIARRHOEA_WITH_DEHYDRATION),AGE_BELOW_FIVE), indParams), "");
		/*
		cohortDsd.addColumn("Diarrhoea with some dehydration", "",
		    ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(DIARRHOEA_WITH_SOME_DEHYDRATION),AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Diarrhoea with severe dehydration", "",
		    ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(DIARRHOEA_WITH_SEVERE_DEHYDRATION),AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Cholera", "",
		    ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getCholeraList(),AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Dysentery", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDysenteryList(),AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Gastroenteritis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(GASTROENTERITIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Pneumonia", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(PNEUMONIA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Severe pneumonia", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SEVERE_PNEUMONIA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Upper Respiratory Tract Infections", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(UPPER_RESPIRATORY_TRACT_INFECTIONS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Lower Respiratory Tract Infections", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(LOWER_RESPIRATORY_TRACT_INFECTIONS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Asthma", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(ASTHMA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Presumed Tuberculosis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(PRESUMED_TUBERCULOSIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Suspected Malaria", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SUSPECTED_MALARIA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Tested for Malaria", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(TESTED_FOR_MALARIA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Confirmed malaria", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CONFIRMED_MALARIA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Ear infection", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(EAR_INFECTION,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Malnutrition", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MALNUTRITION,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Anaemia", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(ANAEMIA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Meningococcal Meningitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MENINGOCOCCAL_MENINGITIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Other Meningitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OTHER_MENINGITIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Neonatal Sepsis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(NEONATAL_SEPSIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Neonatal Tetanus", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(NEONATAL_TETANUS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Poliomyelitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(POLIOMYELITIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Chicken Pox", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CHICKEN_POX,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Measles", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MEASLES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Hepatitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(HEPATITIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Amoebiasis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(AMOEBIASIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Mumps", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MUMPS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Typhoid fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(TYPHOID_FEVER,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Bilharzia Schistosomiasis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(BILHARZIA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Intestinal worms", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(INTESTINAL_WORMS, AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Eye Infections", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(EYE_INFECTIONS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Tonsilitis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(TONSILITIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Urinary Tract Infections", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(URINARY_TRACT_INFECTIONS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Mental Disorders", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(MENTAL_DISORDERS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Dental Disorders", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DENTAL_DISORDERS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Jiggers Infestation", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(JIGGERS_INFESTATION,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Diseases of the skin", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SKIN_DISEASES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Downs syndrome", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DOWNS_SYNDROME, AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Poisoning", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(POISONING,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Road Traffic Injuries", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(ROAD_TRAFFIC_INJURIES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Deaths due to Road Traffic Injuries", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DEATH_BY_ROAD_TRAFFIC_INJURIES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Violence related injuries", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(VIOLENCE_RELATED_INJURIES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Other injuries", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OTHER_INJURIES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Sexual Violence", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SEXUAL_VIOLENCE,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Burns", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(BURNS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Snake Bites", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SNAKE_BITES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Dog Bites", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DOG_BITES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Other Bites", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OTHER_BITES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Diabetes", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DIABETES,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Epilepsy", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(EPILEPSY,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Other Convulsive Disorders", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(OTHER_CONVULSIVE_DISORDERS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Rheumatic Fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(RHEUMATIC_FEVER,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Brucellosis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(BRUCELLOSIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Rickets", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(RICKETS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Cerebral Palsy", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CEREBRAL_PALSY,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Autism", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(AUTISM,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Tryponosomiasis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(TRYPONOSOMIASIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Yellow Fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(YELLOW_FEVER,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Viral Haemorrhagic Fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(VIRAL_HAEMORRHAGIC_FEVER,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Rift valley fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(RIFT_VALLEY_FEVER,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Chikungunya", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CHIKUNGUNYA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Dengue fever", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(DENGUE_FEVER,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Leishmaniasis Kalaazar", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(LEISHMANIASIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Cutaneous leishmaniasis", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(CUTANEOUS_LEISHMANIASIS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Suspected anthrax", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SUSPECTED_ANTHRAX,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Suspected Childhood Cancers", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(SUSPECTED_CHILDHOOD_CANCERS,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Hypoxaemia", "",
				ReportUtils.map(moh705indicatorLibrary.diagnosis(HYPOXAEMIA,AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("All Other Diseases", "",
				ReportUtils.map(moh705indicatorLibrary.allOtherDiseasesUnderFive(AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("New Attendances", "",
				ReportUtils.map(moh705indicatorLibrary.newAttendances(AGE_BELOW_FIVE), indParams), "");
		cohortDsd.addColumn("Re Attendances", "",
				ReportUtils.map(moh705indicatorLibrary.reAttendances(AGE_BELOW_FIVE), indParams), "");*/
		return cohortDsd;
		
	}
}
