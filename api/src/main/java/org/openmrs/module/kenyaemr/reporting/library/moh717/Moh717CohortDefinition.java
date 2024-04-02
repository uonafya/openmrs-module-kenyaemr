/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.moh717;

import org.openmrs.Concept;
import org.openmrs.EncounterType;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyaemr.reporting.commons.MohReportAddonCommons;
import org.openmrs.module.kenyaemr.reporting.mohReportUtils.DiagnosisLists;
import org.openmrs.module.kenyaemr.reporting.library.moh705.Moh705CohortDefinition;
import org.openmrs.module.kenyaemr.reporting.queries.Moh717Queries;
import org.openmrs.module.kenyaemr.util.EhrAddonsConstants;
import org.openmrs.module.reporting.cohort.definition.BaseObsCohortDefinition.TimeModifier;
import org.openmrs.module.reporting.cohort.definition.CodedObsCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.CompositionCohortDefinition;
import org.openmrs.module.reporting.cohort.definition.SqlCohortDefinition;
import org.openmrs.module.reporting.common.SetComparator;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.mohReportUtils.DiagnosisConcepts.getConcept;

@Component
public class Moh717CohortDefinition {

	private final MohReportAddonCommons mohReportAddonCommons;

	private final Moh705CohortDefinition moh705CohortDefinition;

	@Autowired
	public Moh717CohortDefinition(MohReportAddonCommons mohReportAddonCommons, Moh705CohortDefinition moh705CohortDefinition) {
		this.mohReportAddonCommons = mohReportAddonCommons;
		this.moh705CohortDefinition = moh705CohortDefinition;
	}

	public CohortDefinition getAllPatientsWithDiagnosis(int encounter) {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("All patients who have at least diagnosis recorded");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch(
		    "DIAGNOSIS",
		    map(moh705CohortDefinition.getPatientsWhoHaveDiagnosisOverral(encounter),
		        "startDate=${startDate},endDate=${endDate}"));
		cd.setCompositionString("DIAGNOSIS");
		return cd;
	}

	/**
	 * Get new patients
	 *
	 * @return @{@link CohortDefinition}
	 */
	public CohortDefinition getNewPatients() {
		EncounterType registrationInitial = Context.getEncounterService().getEncounterTypeByUuid(
		    "8efa1534-f28f-11ea-b25f-af56118cf21b");
		EncounterType revisitInitial = Context.getEncounterService().getEncounterTypeByUuid(
		    "98d42234-f28f-11ea-b609-bbd062a0383b");
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Get new patients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch(
		    "NEW",
		    map(mohReportAddonCommons.getPatientStates(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.NEW_PATIENT).getConceptId(),
		        registrationInitial.getEncounterTypeId(), revisitInitial.getEncounterTypeId()),
		        "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("VISIT",
		    map(mohReportAddonCommons.getPatientWithNewVisitsBasedOnVisits(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("REVISIT", map(getRevisitPatients(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("WITHIN", map(mohReportAddonCommons.hasEncounter(), "onOrAfter=${startDate},onOrBefore=${endDate}"));

		cd.setCompositionString("(WITHIN AND (NEW OR VISIT)) AND NOT REVISIT");
		return cd;

	}

	/**
	 * Get new patients
	 *
	 * @return @{@link CohortDefinition}
	 */
	public CohortDefinition getRevisitPatients() {
		EncounterType registrationInitial = Context.getEncounterService().getEncounterTypeByUuid(
		    "8efa1534-f28f-11ea-b25f-af56118cf21b");
		EncounterType revisitInitial = Context.getEncounterService().getEncounterTypeByUuid(
		    "98d42234-f28f-11ea-b609-bbd062a0383b");
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Get revisit patients");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch(
		    "RVT",
		    map(mohReportAddonCommons.getPatientStates(getConcept(EhrAddonsConstants._EhrAddOnConcepts.REVISIT_PATIENT)
		            .getConceptId(), registrationInitial.getEncounterTypeId(), revisitInitial.getEncounterTypeId()),
		        "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("VISIT", map(mohReportAddonCommons.getPatientRevisitsBasedOnVisits(), "endDate=${endDate}"));
		cd.addSearch("WITHIN", map(mohReportAddonCommons.hasEncounter(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
		cd.setCompositionString("WITHIN AND (RVT OR VISIT)");
		return cd;

	}

	/**
	 * Get special clinic patients
	 *
	 * @param
	 * @return
	 */
	public CohortDefinition getSpecialClinicPatients() {
		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Special Clinic Patients");
		cd.addParameter(new Parameter("startDate", "startDate", Date.class));
		cd.addParameter(new Parameter("endDate", "endDate", Date.class));
		cd.setQuery(Moh717Queries.getSpecialClinicPatients(EhrAddonsConstants.getConcept(
		    EhrAddonsConstants._EhrAddOnConcepts.SPECIAL_CLINIC).getConceptId()));

		return cd;
	}

	/**
	 * Get special clinic new patients
	 *
	 * @param
	 * @return @{@link CohortDefinition}
	 */
	public CohortDefinition getNewSpecialClinicPatients() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Get new patients on special clinics");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("SPC", map(getSpecialClinicPatients(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("NEW", map(getNewPatients(), "startDate=${startDate},endDate=${endDate}"));

		cd.setCompositionString("SPC AND NEW");
		return cd;
	}

	/**
	 * Get special clinic revisit patients
	 *
	 * @param
	 * @return @{@link CohortDefinition}
	 */
	public CohortDefinition getRevistSpecialClinicPatients() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.setName("Get revisit patients on special clinics");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.addSearch("SPC", map(getSpecialClinicPatients(), "startDate=${startDate},endDate=${endDate}"));
		cd.addSearch("RVT", map(getRevisitPatients(), "startDate=${startDate},endDate=${endDate}"));

		cd.setCompositionString("SPC AND RVT");
		return cd;
	}

	public CohortDefinition getSpecialClinicVisits(Concept concept) {
		CodedObsCohortDefinition cd = new CodedObsCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before date", Date.class));
		cd.setName("Special clinic visits by clinic type");
		cd.setQuestion(EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.SPECIAL_CLINIC));
		cd.setOperator(SetComparator.IN);
		cd.setTimeModifier(TimeModifier.LAST);
		cd.setValueList(Arrays.asList(concept));
		return cd;
	}

	private CohortDefinition getMopcFromTriageOrOpd() {
		SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();
		sqlCohortDefinition.setName("Special clinic from triage and opd");
		sqlCohortDefinition.addParameter(new Parameter("onOrAfter", "Start Date", Date.class));
		sqlCohortDefinition.addParameter(new Parameter("onOrBefore", "End Date", Date.class));
		sqlCohortDefinition.setQuery(Moh717Queries.getMopcClinicQuery(EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.MOPC)
		        .getConceptId(), EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.MOPC_TRAIGE).getConceptId()));
		return sqlCohortDefinition;
	}

	private CohortDefinition getMopcFromOpdLog() {
		SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();
		sqlCohortDefinition.setName("Special clinic from OPD logs");
		sqlCohortDefinition.addParameter(new Parameter("onOrAfter", "Start Date", Date.class));
		sqlCohortDefinition.addParameter(new Parameter("onOrBefore", "End Date", Date.class));
		sqlCohortDefinition.setQuery(Moh717Queries.getMopcFromOpdLog(EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.MOPC)
		        .getConceptId()));
		return sqlCohortDefinition;
	}

	public CohortDefinition getMopSpecialClinic() {
		CompositionCohortDefinition cd = new CompositionCohortDefinition();
		cd.addParameter(new Parameter("onOrAfter", "After date", Date.class));
		cd.addParameter(new Parameter("onOrBefore", "Before date", Date.class));
		cd.setName("MOPC clinic");
		cd.addSearch(
		    "CLINIC",
		    map(getSpecialClinicVisits(EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.MOPC)),
		        "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("TOPD", map(getMopcFromTriageOrOpd(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));
		cd.addSearch("LOG", map(getMopcFromOpdLog(), "onOrAfter=${onOrAfter},onOrBefore=${onOrBefore}"));

		cd.setCompositionString("(CLINIC OR TOPD OR LOG)");
		return cd;
	}

	public CohortDefinition getSpecialClinicPatientsOutOfRange() {
		SqlCohortDefinition sqlCohortDefinition = new SqlCohortDefinition();
		sqlCohortDefinition.setName("Special clinic reports out of range");
		sqlCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		sqlCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));
		sqlCohortDefinition.setQuery(Moh717Queries.getSpecialClinicPatientsOutsideRange(
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.SPECIAL_CLINIC).getConceptId(),
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.ENT).getConceptId(), EhrAddonsConstants
		            .getConcept(EhrAddonsConstants._EhrAddOnConcepts.EYE_CLINIC).getConceptId(), EhrAddonsConstants
		            .getConcept(EhrAddonsConstants._EhrAddOnConcepts.TBL).getConceptId(),
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.STI).getConceptId(), EhrAddonsConstants
		            .getConcept(EhrAddonsConstants._EhrAddOnConcepts.CCC_CLINIC).getConceptId(), EhrAddonsConstants
		            .getConcept(EhrAddonsConstants._EhrAddOnConcepts.PSYCHIATRIC_CLINIC).getConceptId(), EhrAddonsConstants
		            .getConcept(EhrAddonsConstants._EhrAddOnConcepts.ORT).getConceptId(),
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.OCP).getConceptId(), EhrAddonsConstants
		            .getConcept(EhrAddonsConstants._EhrAddOnConcepts.PHYS).getConceptId(),
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.SC).getConceptId(), EhrAddonsConstants
		            .getConcept(EhrAddonsConstants._EhrAddOnConcepts.PAED).getConceptId(),
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.PAED).getConceptId(), EhrAddonsConstants
		            .getConcept(EhrAddonsConstants._EhrAddOnConcepts.MOPC).getConceptId(),
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.NUTRITION_PROGRAM).getConceptId(),
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.ONCOLOGY_CLINIC).getConceptId(),
		    EhrAddonsConstants.getConcept(EhrAddonsConstants._EhrAddOnConcepts.RENAL_CLINIC).getConceptId()));
		return sqlCohortDefinition;
	}

	public CohortDefinition getDentalSpecialClinic(int c1, int c2, int encounter) {
		CompositionCohortDefinition compositionCohortDefinition = new CompositionCohortDefinition();
		compositionCohortDefinition.setName("Include dental diagnosis");
		compositionCohortDefinition.addParameter(new Parameter("startDate", "Start Date", Date.class));
		compositionCohortDefinition.addParameter(new Parameter("endDate", "End Date", Date.class));

		SqlCohortDefinition cd = new SqlCohortDefinition();
		cd.setName("Special clinic - Dental");
		cd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		cd.addParameter(new Parameter("endDate", "End Date", Date.class));
		cd.setQuery("SELECT p.patient_id FROM patient p " + " INNER JOIN encounter e ON p.patient_id=e.patient_id"
		        + " INNER JOIN obs o ON e.encounter_id=o.encounter_id "
		        + " WHERE e.encounter_datetime BETWEEN :startDate AND :endDate "
		        + " AND p.voided=0 AND e.voided=0 AND o.voided=0 " + " AND o.value_coded IN(" + c1 + "," + c2 + ")");

		compositionCohortDefinition.addSearch("SPD", map(cd, "startDate=${startDate},endDate=${endDate}"));
		compositionCohortDefinition.addSearch(
		    "DIAGNOSIS",
		    map(moh705CohortDefinition.getPatientsWhoHaveDiagnosis705(DiagnosisLists.getDentalDisordersList(), encounter),
		        "startDate=${startDate},endDate=${endDate}"));
		compositionCohortDefinition.setCompositionString("SPD OR DIAGNOSIS");
		return compositionCohortDefinition;
	}
}
