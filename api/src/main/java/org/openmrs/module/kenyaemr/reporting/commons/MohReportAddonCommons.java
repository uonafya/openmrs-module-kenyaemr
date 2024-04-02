/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.commons;

import org.apache.commons.lang3.StringUtils;
import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.Program;
import org.openmrs.module.kenyacore.report.cohort.definition.CalculationCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.AgeCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.BaseObsCohortDefinition.TimeModifier;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.EncounterCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.GenderCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.ProgramEnrollmentCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.DurationUnit;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.common.TimeQualifier;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class MohReportAddonCommons {

	/**
	 * Patients who have encounters on date
	 *
	 * @return @{@link CohortDefinition}
	 */
	public CohortDefinition getPatientsHavingEncountersOnDate(int day) {
		CalculationCohortDefinition cd = new CalculationCohortDefinition("Encounters per day",
		        new EncountersBasedOnDaySuppliedCalculation());
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addCalculationParameter("day", day);
		return cd;
	}

	/**
	 * Get patients dimesion age
	 *
	 * @param minAge
	 * @param maxAge
	 * @return
	 */
	public CohortDefinition createXtoYAgeCohort(Integer minAge, Integer maxAge) {
		AgeCohortDefinition xToYCohort = new AgeCohortDefinition();
		xToYCohort.setName("age");
		if (minAge != null) {
			xToYCohort.setMinAge(minAge);
		}
		if (maxAge != null) {
			xToYCohort.setMaxAge(maxAge);
		}
		xToYCohort.addParameter(new Parameter("effectiveDate", "effectiveDate", Date.class));
		return xToYCohort;
	}

	/**
	 * Patients who are female
	 *
	 * @return the cohort definition
	 */
	public CohortDefinition femaleCohort() {
		GenderCohortDefinition cohort = new GenderCohortDefinition();
		cohort.setName("femaleCohort");
		cohort.setFemaleIncluded(true);
		cohort.setMaleIncluded(false);
		return cohort;
	}

	/**
	 * Patients who are male
	 *
	 * @return the cohort definition
	 */
	public CohortDefinition maleCohort() {
		GenderCohortDefinition cohort = new GenderCohortDefinition();
		cohort.setName("maleCohort");
		cohort.setMaleIncluded(true);
		cohort.setFemaleIncluded(false);
		return cohort;
	}

	/**
	 * Get patients who are new to the facility
	 *
	 * @return @CohortDefinition
	 */
	public CohortDefinition getPatientStates(int answer, int enc1, int enc2) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("has obs between dates");
		cd.addParameter(new Parameter("endDate", "Before Date", Date.class));
		cd.addParameter(new Parameter("startDate", "After Date", Date.class));
		String sql = " SELECT patient_id FROM( " + " SELECT max_enc.patient_id, max_enc.encounter_date FROM ( "
		        + " SELECT p.patient_id AS patient_id, MAX(e.encounter_datetime) AS encounter_date FROM patient p "
		        + " INNER JOIN encounter e " + " ON p.patient_id=e.patient_id " + " WHERE " + " p.voided=0 "
		        + " AND e.voided=0" + " AND e.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND e.encounter_type IN("
		        + enc1
		        + ","
		        + enc2
		        + ")"
		        + " GROUP BY p.patient_id) max_enc "

		        + " INNER JOIN "
		        + " ( "
		        + " SELECT pp.patient_id AS patient_id, ee.encounter_datetime AS encounter_date FROM patient pp "
		        + "  INNER JOIN encounter ee ON pp.patient_id=ee.patient_id "
		        + " INNER JOIN obs o ON ee.encounter_id=o.encounter_id "
		        + " WHERE ee.encounter_type IN ("
		        + enc1
		        + ","
		        + enc2
		        + ")"
		        + " AND ee.voided= 0  "
		        + " AND pp.voided = 0 "
		        + " AND o.voided=0 "
		        + " AND ee.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND o.value_coded ="
		        + answer
		        + " )max_obs ON max_enc.patient_id=max_obs.patient_id "

		        + " WHERE max_enc.encounter_date = max_obs.encounter_date) out_table ";
		cd.setQuery(sql);
		return cd;
	}

	public CohortDefinition getPatientRevisitsBasedOnVisits() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Has visits within a period of time REVISITS one year form the current end visit date");
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery("SELECT tbl.patient_id FROM( "
		        + " SELECT p.patient_id, COUNT(visit_id) FROM patient p INNER JOIN visit v ON p.patient_id=v.patient_id "
		        + " WHERE v.date_started BETWEEN DATE_ADD(:endDate, INTERVAL -12 MONTH) AND :endDate "
		        + " GROUP BY p.patient_id HAVING COUNT(visit_id) > 1) tbl ");

		return cd;
	}

	public CohortDefinition getPatientWithNewVisitsBasedOnVisits() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Has visits within a period of time NEW VISITS");
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.setQuery("SELECT tbl.patient_id FROM( "
		        + " SELECT p.patient_id, COUNT(visit_id) FROM patient p INNER JOIN visit v ON p.patient_id=v.patient_id "
		        + " WHERE v.date_started BETWEEN :startDate AND  :endDate "
		        + " GROUP BY p.patient_id HAVING COUNT(visit_id) <= 1) tbl ");

		return cd;
	}

	/**
	 * Patients who at most maxAge years old on ${effectiveDate}
	 *
	 * @return the cohort definition
	 */
	public CohortDefinition agedAtMost(int maxAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged at most " + maxAge);
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMaxAge(maxAge);
		return cd;
	}

	/**
	 * Patients who are at least minAge years old on ${effectiveDate}
	 *
	 * @return the cohort definition
	 */
	public CohortDefinition agedAtLeast(int minAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged at least " + minAge);
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMinAge(minAge);
		return cd;
	}

	/**
	 * patients who are at least minAge years old and at most years old on ${effectiveDate}
	 *
	 * @return CohortDefinition
	 */
	public CohortDefinition agedAtLeastAgedAtMost(int minAge, int maxAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged between " + minAge + " and " + maxAge + " years");
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMinAge(minAge);
		cd.setMaxAge(maxAge);
		return cd;
	}

	/**
	 * patients who are at least minAge months old and at most months old on ${effectiveDate}
	 *
	 * @return CohortDefinition
	 */
	public CohortDefinition agedAtLeastAgedAtMostInMonths(int minAge, int maxAge) {
		AgeCohortDefinition cd = new AgeCohortDefinition();
		cd.setName("aged between " + minAge + " and " + maxAge + " years");
		cd.addParameter(new Parameter("effectiveDate", "Effective Date", Date.class));
		cd.setMinAge(minAge);
		cd.setMaxAge(maxAge);
		cd.setMinAgeUnit(DurationUnit.MONTHS);
		cd.setMaxAgeUnit(DurationUnit.MONTHS);
		return cd;
	}

	/**
	 * Patients who have an encounter between ${onOrAfter} and ${onOrBefore}
	 *
	 * @param types the encounter types
	 * @return the cohort definition
	 */
	public CohortDefinition hasEncounter(EncounterType... types) {
		EncounterCohortDefinition cd = new EncounterCohortDefinition();
		cd.setName("has encounter between dates");
		cd.setTimeQualifier(TimeQualifier.ANY);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		if (types.length > 0) {
			cd.setEncounterTypeList(Arrays.asList(types));
		}
		return cd;
	}

	/**
	 * Patients who have an obs between ${onOrAfter} and ${onOrBefore}
	 *
	 * @param question the question concept
	 * @param answers the answers to include
	 * @return the cohort definition
	 */
	public CohortDefinition hasObs(Concept question, Concept... answers) {
		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.setName("has obs between dates");
		cd.setQuestion(question);
		cd.setOperator(SetComparator.IN);
		cd.setTimeModifier(TimeModifier.ANY);
		cd.addParameter(new Parameter("onOrBefore", "Before Date", Date.class));
		cd.addParameter(new Parameter("onOrAfter", "After Date", Date.class));
		if (answers.length > 0) {
			cd.setValueList(Arrays.asList(answers));
		}
		return cd;
	}

	/**
	 * Patients enrolled in different programs within the EHR
	 */
	public CohortDefinition programEnrollment(Program... programs) {
		ProgramEnrollmentCohortDefinition cd = new ProgramEnrollmentCohortDefinition();
		cd.setName("enrolled in program between dates");
		cd.addParameter(new Parameter("enrolledOnOrAfter", "After Date", Date.class));
		cd.addParameter(new Parameter("enrolledOnOrBefore", "Before Date", Date.class));
		if (programs.length > 0) {
			cd.setPrograms(Arrays.asList(programs));
		}
		return cd;
	}

	public CohortDefinition patientHasEncounter(List<Integer> typesIds) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("has encounter between dates with the given types");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " WHERE e.encounter_datetime BETWEEN :startDate AND :endDate AND p.voided=0 AND e.voided=0 "
		        + " AND e.encounter_type IN(" + StringUtils.join(typesIds, ',') + ")");
		return cd;
	}

	public CohortDefinition patientHasEncounterAndForms(List<Integer> typesIds, List<Integer> formIds) {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("has encounter between dates with the given types with the given forms");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery("SELECT p.patient_id FROM patient p INNER JOIN encounter e ON p.patient_id=e.patient_id "
		        + " WHERE e.encounter_datetime BETWEEN :startDate AND :endDate AND p.voided=0 AND e.voided=0 "
		        + " AND e.encounter_type IN(" + StringUtils.join(typesIds, ',') + ") " + " AND e.form_id IN("
		        + StringUtils.join(formIds, ',') + ") ");
		return cd;
	}
}
