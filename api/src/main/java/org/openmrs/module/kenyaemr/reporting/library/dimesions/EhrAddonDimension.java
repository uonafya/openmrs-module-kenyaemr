/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.dimesions;

import org.openmrs.module.kenyaemr.reporting.commons.MohReportAddonCommons;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717CohortDefinition;
import org.openmrs.module.kenyaemr.util.EhrAddonsConstants;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.indicator.dimension.CohortDefinitionDimension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.util.EhrAddonsConstants.getConcept;

@Component
public class EhrAddonDimension {

	private final MohReportAddonCommons commonLibrary;

	private final Moh717CohortDefinition moh717CohortDefinition;

	@Autowired
	public EhrAddonDimension(MohReportAddonCommons commonLibrary, Moh717CohortDefinition moh717CohortDefinition) {
		this.commonLibrary = commonLibrary;
		this.moh717CohortDefinition = moh717CohortDefinition;
	}

	/**
	 * Patients with encounters on a given date
	 *
	 * @return @{@link org.openmrs.module.reporting.indicator.dimension.CohortDimension}
	 */
	public CohortDefinitionDimension encountersOfMonthPerDay() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Patient with encounters on date of day");
		dim.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dim.addParameter(new Parameter("endDate", "End Date", Date.class));
		dim.addCohortDefinition("1", map(getPatientsSeenOnDay(0), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("2", map(getPatientsSeenOnDay(1), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("3", map(getPatientsSeenOnDay(2), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("4", map(getPatientsSeenOnDay(3), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("5", map(getPatientsSeenOnDay(4), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("6", map(getPatientsSeenOnDay(5), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("7", map(getPatientsSeenOnDay(6), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("8", map(getPatientsSeenOnDay(7), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("9", map(getPatientsSeenOnDay(8), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("10", map(getPatientsSeenOnDay(9), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("11", map(getPatientsSeenOnDay(10), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("12", map(getPatientsSeenOnDay(11), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("13", map(getPatientsSeenOnDay(12), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("14", map(getPatientsSeenOnDay(13), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("15", map(getPatientsSeenOnDay(14), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("16", map(getPatientsSeenOnDay(15), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("17", map(getPatientsSeenOnDay(16), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("18", map(getPatientsSeenOnDay(17), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("19", map(getPatientsSeenOnDay(18), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("20", map(getPatientsSeenOnDay(19), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("21", map(getPatientsSeenOnDay(20), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("22", map(getPatientsSeenOnDay(21), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("23", map(getPatientsSeenOnDay(22), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("24", map(getPatientsSeenOnDay(23), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("25", map(getPatientsSeenOnDay(24), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("26", map(getPatientsSeenOnDay(25), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("27", map(getPatientsSeenOnDay(26), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("28", map(getPatientsSeenOnDay(27), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("29", map(getPatientsSeenOnDay(28), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("30", map(getPatientsSeenOnDay(29), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("31", map(getPatientsSeenOnDay(30), "startDate=${startDate},endDate=${endDate}"));

		return dim;
	}

	/**
	 * Get age based on the based on DOB and context date
	 *
	 * @return @{@link org.openmrs.module.reporting.indicator.dimension.CohortDimension}
	 */
	public CohortDefinitionDimension getAge() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		dim.setName("Patient age - dim");
		dim.addCohortDefinition("<5", map(commonLibrary.createXtoYAgeCohort(0, 4), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition(">5", map(commonLibrary.createXtoYAgeCohort(5, 59), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition(">60", map(commonLibrary.createXtoYAgeCohort(60, 600), "effectiveDate=${effectiveDate}"));
		return dim;
	}

	/**
	 *
	 *
	*/
	public CohortDefinitionDimension getStandardAge() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		dim.setName("Standard age");
		dim.addCohortDefinition("0-1", map(commonLibrary.createXtoYAgeCohort(0, 1), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("10-14", map(commonLibrary.createXtoYAgeCohort(10, 14), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15-19", map(commonLibrary.createXtoYAgeCohort(15, 19), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("20-24", map(commonLibrary.createXtoYAgeCohort(20, 24), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("15+", map(commonLibrary.createXtoYAgeCohort(15, 150), "effectiveDate=${effectiveDate}"));

		dim.addCohortDefinition("0-9", map(commonLibrary.createXtoYAgeCohort(0, 9), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("10-17", map(commonLibrary.createXtoYAgeCohort(10, 17), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("18-49", map(commonLibrary.createXtoYAgeCohort(10, 17), "effectiveDate=${effectiveDate}"));
		dim.addCohortDefinition("50+", map(commonLibrary.createXtoYAgeCohort(50, 150), "effectiveDate=${effectiveDate}"));

		return dim;
	}

	/**
	 * Get patient gender
	 *
	 * @return @{@link org.openmrs.module.reporting.indicator.dimension.CohortDimension}
	 */
	public CohortDefinitionDimension getGender() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("gender");
		dim.addCohortDefinition("F", map(commonLibrary.femaleCohort(), ""));
		dim.addCohortDefinition("M", map(commonLibrary.maleCohort(), ""));
		return dim;
	}

	/**
	 * Get patients who fall in a special clinic
	 *
	 * @return @{@link org.openmrs.module.reporting.indicator.dimension.CohortDimension}
	 */
	public CohortDefinitionDimension getSpecialClinicVisits() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Special clinic visits");
		dim.addParameter(new Parameter("onOrAfter", "After date", Date.class));
		dim.addParameter(new Parameter("onOrBefore", "Before date", Date.class));
		dim.addCohortDefinition(
		    "ENT",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.ENT)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "EYE",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.EYE_CLINIC)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "TBL",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.TBL)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "STI",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.STI)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "CCC",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.CCC_CLINIC)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "PSY",
		    map(moh717CohortDefinition
		            .getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.PSYCHIATRIC_CLINIC)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "ORT",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.ORT)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "OCP",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.OCP)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "PHYS",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.PHYS)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "SC",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.SC)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "PAED",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.PAED)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "OG",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.OG)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "NUC",
		    map(moh717CohortDefinition
		            .getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.NUTRITION_PROGRAM)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "ONC",
		    map(moh717CohortDefinition
		            .getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.ONCOLOGY_CLINIC)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		dim.addCohortDefinition(
		    "RENAL",
		    map(moh717CohortDefinition.getSpecialClinicVisits(getConcept(EhrAddonsConstants._EhrAddOnConcepts.RENAL_CLINIC)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		return dim;
	}

	public CohortDefinition getPatientsSeenOnDay(int day) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Get patients seen on a given day");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id"
		        + " WHERE p.voided= 0 AND e.voided = 0 AND e.encounter_datetime BETWEEN DATE_ADD(:startDate, INTERVAL "
		        + day + " DAY) AND DATE_ADD(DATE_ADD(DATE_ADD(:startDate, INTERVAL " + day
		        + " DAY), INTERVAL 23 HOUR), INTERVAL 59 MINUTE) AND e.encounter_datetime <= :endDate");
		return cd;
	}

	public CohortDefinitionDimension newOrRevisits() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("New or revisits patients");
		dim.addParameter(new Parameter("startDate", "After date", Date.class));
		dim.addParameter(new Parameter("endDate", "Before date", Date.class));
		dim.addCohortDefinition("RVT",
		    map(moh717CohortDefinition.getRevisitPatients(), "startDate=${startDate},endDate=${endDate}"));
		dim.addCohortDefinition("NEW",
		    map(moh717CohortDefinition.getNewPatients(), "startDate=${startDate},endDate=${endDate}"));
		return dim;
	}

	/**
	 * Dimension of age between
	 *
	 * @return Dimension
	 */
	public CohortDefinitionDimension moh710AgeGroups() {
		CohortDefinitionDimension dim = new CohortDefinitionDimension();
		dim.setName("Fine age between(<1,>=1)");
		dim.addParameter(new Parameter("onDate", "Date", Date.class));
		dim.addCohortDefinition("<1", map(commonLibrary.agedAtMost(0), "effectiveDate=${onDate}"));
		dim.addCohortDefinition(">=1", map(commonLibrary.agedAtLeast(1), "effectiveDate=${onDate}"));
		dim.addCohortDefinition("18-24", map(commonLibrary.agedAtLeastAgedAtMostInMonths(18, 24), "effectiveDate=${onDate}"));
		dim.addCohortDefinition(">2", map(commonLibrary.agedAtLeast(2), "effectiveDate=${onDate}"));
		return dim;
	}
}
