/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.indicator;

import org.openmrs.module.kenyaemr.reporting.library.moh705.Moh705CohortDefinition;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.openmrs.module.kenyaemr.reporting.mohReportUtils.ReportingUtils.cohortIndicator;
import static org.openmrs.module.reporting.evaluation.parameter.Mapped.map;

@Component
public class Moh705IndicatorDefinition {

	private final Moh705CohortDefinition moh705CohortDefinition;

	@Autowired
	public Moh705IndicatorDefinition(Moh705CohortDefinition moh705aCohortDefinition) {
		this.moh705CohortDefinition = moh705aCohortDefinition;
	}

	//Diagnonosis 705A
	public CohortIndicator getAllChildrenPatientsWithDiagnosis(List<Integer> list, int encounter) {
		return cohortIndicator(
		    "Diagnosis",
		    map(moh705CohortDefinition.getPatientsWhoHaveDiagnosis705AWithAge(list, encounter),
		        "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getAllChildrenPatientsWithDiagnosisForMalaria(List<Integer> list, List<Integer> ans, int encounter) {
		return cohortIndicator(
		    "Diagnosis for malaria for 705 A",
		    map(moh705CohortDefinition.getMalariaDiagnosis705A(list, ans, encounter),
		        "startDate=${startDate},endDate=${endDate}"));
	}

	//Diagnonosis 705B
	public CohortIndicator getAllAdultPatientsWithDiagnosis(List<Integer> list, int encounter) {
		return cohortIndicator(
		    "Diagnosis",
		    map(moh705CohortDefinition.getPatientsWhoHaveDiagnosis705BWithAge(list, encounter),
		        "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getAllAdultPatientsWithOtherDiagnosis(List<Integer> list, int encounter) {
		return cohortIndicator(
		    "Other Diagnosis for adults",
		    map(moh705CohortDefinition.getExactOtherDiagnosisForPatientsMOH705B(list, encounter),
		        "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getAllChildredPatientsWithOtherDiagnosis(List<Integer> list, int encounter) {
		return cohortIndicator(
		    "Other Diagnosis for Children",
		    map(moh705CohortDefinition.getExactOtherDiagnosisForPatientsMOH705A(list, encounter),
		        "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getAllAdultPatientsWithDiagnosisForMalaria(List<Integer> list, List<Integer> ans, int encounter) {
		return cohortIndicator(
		    "Diagnosis for malaria for 705 B",
		    map(moh705CohortDefinition.getMalariaDiagnosis705B(list, ans, encounter),
		        "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getNewChildrenPatients() {
		return cohortIndicator("New children patients",
		    map(moh705CohortDefinition.getNewChildrenPatients(), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getNewAdultsPatients() {
		return cohortIndicator("New adults patients",
		    map(moh705CohortDefinition.getNewAdultsPatients(), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getRevisitsChildrenPatients() {
		return cohortIndicator("Revisit children patients",
		    map(moh705CohortDefinition.getRevisitsChildrenPatients(), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getRevisitsAdultsPatients() {
		return cohortIndicator("Revisit adults patients",
		    map(moh705CohortDefinition.getRevisitAdultsPatients(), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getAllChildrenPatientsReferrals(int question, int ans) {
		return cohortIndicator(
		    "Get children patients with referral",
		    map(moh705CohortDefinition.getAllChildrenPatientsReferrals(question, ans),
		        "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getAllAdultPatientsWithReferrals(int question, int ans) {
		return cohortIndicator(
		    "Get adults patients with referral",
		    map(moh705CohortDefinition.getAllAdultsPatientsReferrals(question, ans),
		        "startDate=${startDate},endDate=${endDate}"));
	}
}
