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
import org.openmrs.module.kenyaemr.reporting.mohReportUtils.DiagnosisLists;
import org.openmrs.module.kenyaemr.reporting.mohReportUtils.ReportAddonUtils;
import org.openmrs.module.kenyaemr.reporting.mohReportUtils.ReportingUtils;
import org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH705.MOH705IndicatorLibrary;
import org.openmrs.module.kenyaemr.reporting.library.shared.common.CommonDimensionLibrary;
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
@Builds({"kenyaemr.common.report.moh705B"})
public class MOH705BReportBuilder extends AbstractReportBuilder {
    static final String EQUAL_AND_OVER_FIVE = ">=5";
    static final int DIARRHOEA = 5018;
    static final int TUBERCULOSIS = 160156;
    static final int DYSENTRY = 152;
    static final int CHOLERA = 145622;
    static final int MENINGOCOCCAL_MENINGITIS = 134369;
    static final int OTHER_MENINGITIS = 0;
    static final int TETANUS = 124957;
    static final int POLIOMYELITIS = 5258;
    static final int CHICKEN_POX = 892;
    static final int MEASLES = 134561;
    static final int HEPATITIS = 116986;
    static final int MUMPS = 133671;
    static final int SUSPECTED_MALARIA = 166623;
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
    static final int REFERRALS_FROM_OTHER_HEALTH_FACILITY = 0;
    static final int REFERRALS_FROM_OTHER_COMMUNITY_UNIT = 0;
    private static final int REFERRALS_TO_COMMUNITY_UNIT =  0;


    @Autowired
    private MOH705IndicatorLibrary moh705indicatorLibrary;

    private final CommonDimensionLibrary commonDimensionLibrary;

    @Autowired
    public MOH705BReportBuilder(MOH705IndicatorLibrary moh705indicatorLibrary, CommonDimensionLibrary commonDimensionLibrary) {
        this.moh705indicatorLibrary = moh705indicatorLibrary;
        this.commonDimensionLibrary = commonDimensionLibrary;
    }


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
        String indParams = "startDate=${startDate},endDate=${endDate}";

        CohortIndicatorDataSetDefinition cohortDsd = new CohortIndicatorDataSetDefinition();
        cohortDsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
        cohortDsd.addParameter(new Parameter("endDate", "End Date", Date.class));
        cohortDsd.setName("MOH705B");
        cohortDsd.setDescription("MOH 705B");
        cohortDsd.addDimension("day", ReportUtils.map(commonDimensionLibrary.encountersOfMonthPerDay(), indParams));


        ReportingUtils.addRow(cohortDsd, "DA", "Diarrhoea", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getDiarrheaDiagnosisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "TBA", "Tuberculosis", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getTuberculosisDiagnosisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "DYA", "Dysentery",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDysenteryList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "CLA", "Cholera",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getCholeraList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "MMA", "Meningococcal Meningitis", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getMeningococcalMeningitisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "MOA", "Other Meningitis", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherMenigitisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "NTA", "Neonatal Tetanus", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getNeonatalTetanusList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "PMA", "Poliomyelitis", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getPoliomyelitisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils
                .addRow(cohortDsd, "CPA", "Chicken Pox", ReportUtils.map(
                                moh705indicatorLibrary.diagnosis(DiagnosisLists.getChickenPoxList(), EQUAL_AND_OVER_FIVE), indParams),
                        ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "MEA", "Measles",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMeaslesList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "HEA", "Hepatitis",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getHepatitisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "MPSA", "Mumps",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getMumpsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(
                cohortDsd,
                "SUA",
                "Suspected malaria",
                ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getMalariaList(),EQUAL_AND_OVER_FIVE), indParams),
                        /*moh705indicatorLibrary.diagnosis(DiagnosisLists.getMalariaList(),  //TODO port getSuspectedMalariaResults evaluation criteria
                                DiagnosisLists.getSuspectedMalariaResults()), indParam),*/
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(
                cohortDsd,
                "COA",
                "Confirmed Malaria positive",
                ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getMalariaList(),
                           EQUAL_AND_OVER_FIVE), indParams),
                           /* DiagnosisLists.getConfirmedMalariaResults()), indParam),*/ //TODO getConfirmedMalariaResults criteria
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "MPA", "Malaria In Pregnancy", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getMalariaInPregnancyList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "TYA", "Typhoid Fever",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getTyphoidList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "STIA", "STI", ReportUtils.map(
                moh705indicatorLibrary.diagnosis(DiagnosisLists.getSexuallyTransmittedInfectionsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "URA", "Urinary tract infection", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getUrinaryTractInfectionList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "BIA", "Bilharzia",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getBilharziaList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "INA", "Intestinal worms", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getInterstinalwormsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "MLA", "Malnutrition", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getMalnutritionList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "ANEA", "Aneamia",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAnaemiaList(), EQUAL_AND_OVER_FIVE), indParams),

                ReportAddonUtils.getAdultChildrenColumns());
        ReportingUtils.addRow(cohortDsd, "EYA", "Eye Infections", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getEyeInfectionsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "EIA", "Ear Infection Conditions", ReportUtils.map(
                                moh705indicatorLibrary.diagnosis(DiagnosisLists.getEarInfectionConditionsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "UPA", "Upper Respiratory Tract Infections", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getUpperRespiratoryTractInfectionsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "ASA", "Asthma",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAsthmaList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "PNA", "Pneumonia",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getPneumoniaList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "OTRA", "Other Dis Of Respiratory System",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherDisOfRespiratorySystemList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "ABA", "Abortion",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAbortionList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "DPA", "Dis Of Puerperium & Childbath", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getDisOfPuerperiumChildbathList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "HYA", "Hypertension", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getHypertensionList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "MDA", "Mental Disorders", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getMentalDisordersList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "DEA", "Dental Disorders", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getDentalDisordersList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "JIA", "Jiggers Infestation", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getJiggersInfestationList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "DSA", "Disease Of The Skin", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getDiseaseOfTheSkinList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "AJPA", "Anthritis Joint Pains", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getAnthritisJointPainsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "POA", "Poisoning", ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getPoisoningList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "ROA", "Road Traffic Injuries", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getRoadTrafficInjuriesList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "OIA", "Other Injuries", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherInjuriesList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "SEA", "Sexual Assault", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getSexualAssaultList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "VRA", "Violence Related Injuries", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getViolenceRelatedInjuriesList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "BUA", "Burns", ReportUtils.map(
                moh705indicatorLibrary.diagnosis(DiagnosisLists.getBurnsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "SNA", "Snake Bites", ReportUtils.map(
                moh705indicatorLibrary.diagnosis(DiagnosisLists.getSnakeBitesList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "DOA", "Dog Bites",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDogBitesList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils
                .addRow(cohortDsd, "OBA", "Other Bites", ReportUtils.map(
                                moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherBitesList(), EQUAL_AND_OVER_FIVE), indParams),
                        ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "DTA", "Diabetes",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getDiabetesList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "EPA", "Epilepsy",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getEpilepsyList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "BRLA", "Brucellosis", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getBrucellosisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils
                        .getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "CAA", "Cardiovascular Conditions", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getCardiovascularConditionsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "CNSA", "Central Nervous System Conditions", ReportUtils.map(
                moh705indicatorLibrary.diagnosis(DiagnosisLists.getOtherCentralNervousSystemConditionsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "OVA", "Overweight", ReportUtils.map(
                moh705indicatorLibrary.diagnosis(DiagnosisLists.getOvrerweightList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils
                .getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "MSA", "Muscular Skeletal Conditions",
                ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getMuscularSkeletalConditionsList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "FIA", "Fistula Birth Related", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getFistulaBirthRelatedList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "NSA", "Neoplams",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getNeoplamsList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "PHA", "Physical Disability", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getPhysicalDisabilityList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "TRA", "Tryponomiasis", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getTryponomiasisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "KAA", "Kalazar Leishmaniasis", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getKalazarLeishmaniasisList(), EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "YEA", "Yellow Fever", ReportUtils.map(
                moh705indicatorLibrary.diagnosis(DiagnosisLists.getYellowFeverList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils
                .getAdultChildrenColumns());

        ReportingUtils
                .addRow(cohortDsd, "VHA", "Viral Haemorrhagic Fever", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getViralHaemorrhagicFeverList(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "DRA",
                "Death due to road traffic injuries",
                /*ReportUtils.map(moh705indicatorLibrary.diagnosis(
                        getConcept("1599AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getConceptId(),
                        getConcept("1603AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getConceptId()), indParam),*/
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getRoadTrafficInjuriesList(),EQUAL_AND_OVER_FIVE), indParams), //TODO change this to read Death due to road traffic injuries
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "AODA", "All other diseases for adults", ReportUtils.map(
                moh705indicatorLibrary.diagnosis(DiagnosisLists.getAllOtherDiseasesListForAdults(), EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "NFAA", "No. Of First Attendances",
                ReportUtils.map(moh705indicatorLibrary.newAttendances(EQUAL_AND_OVER_FIVE)), ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils
                .addRow(cohortDsd, "RETA", "Re-Attendances",
                        ReportUtils.map(moh705indicatorLibrary.reAttendances(EQUAL_AND_OVER_FIVE)),
                        ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(
                cohortDsd,
                "RFHA",
                "Referrals From Other Health Facility",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(REFERRALS_FROM_OTHER_HEALTH_FACILITY),EQUAL_AND_OVER_FIVE), indParams),
                /*ReportUtils.map(moh705indicatorLibrary.getAllAdultPatientsWithReferrals( TODO  port in this function
                        getConcept("160481AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getConceptId(),
                        getConcept("1537AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getConceptId()), indParam),*/
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(
                cohortDsd,
                "RFCA",
                "Referrals From Other Community Unit",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(REFERRALS_FROM_OTHER_COMMUNITY_UNIT),EQUAL_AND_OVER_FIVE), indParams),
                /*ReportUtils.map(moh705indicatorLibrary.getAllAdultPatientsWithReferrals( //TODO implement getAllAdultPatientsWithReferrals
                        getConcept("160481AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA").getConceptId(),
                        getConcept("4fcf003e-71cf-47a5-a967-47d24aa61092").getConceptId()), indParam),*/
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(
                cohortDsd,
                "RTCA",
                "Referrals To Community Unit",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(Arrays.asList(REFERRALS_TO_COMMUNITY_UNIT),EQUAL_AND_OVER_FIVE), indParams),
               /* ReportUtils.map(moh705indicatorLibrary.getAllAdultPatientsWithReferrals(
                        getConcept("477a7484-0f99-4026-b37c-261be587a70b").getConceptId(),
                        getConcept("4fcf003e-71cf-47a5-a967-47d24aa61092").getConceptId()), indParam),*/
                ReportAddonUtils.getAdultChildrenColumns());

        //additional indicators added
        ReportingUtils.addRow(cohortDsd, "AM", "AMOEBIASIS",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAmoebiasis(),EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "RVF", "Rift valley fever", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getRiftValleyFeverList(),EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "CKU", "Chikungunya fever", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getChikungunyaFeverList(),EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "DENF", "Dengue fever", ReportUtils.map(
                moh705indicatorLibrary.diagnosis(DiagnosisLists.getDengueFeverList(),EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils
                .getAdultChildrenColumns());

        ReportingUtils
                .addRow(cohortDsd, "CL", "Cutaneous Leishmaniasis", ReportUtils.map(
                        moh705indicatorLibrary.diagnosis(DiagnosisLists.getCutaneousLeishmaniasisList(),EQUAL_AND_OVER_FIVE), indParams), ReportAddonUtils.getAdultChildrenColumns());

        ReportingUtils.addRow(cohortDsd, "ANT", "Suspected Anthrax",
                ReportUtils.map(moh705indicatorLibrary.diagnosis(DiagnosisLists.getAnthraxList(),EQUAL_AND_OVER_FIVE), indParams),
                ReportAddonUtils.getAdultChildrenColumns());


        return cohortDsd;
    }
}
