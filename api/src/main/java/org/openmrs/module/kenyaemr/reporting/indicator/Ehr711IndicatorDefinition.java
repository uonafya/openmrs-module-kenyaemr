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

import org.openmrs.Concept;
import org.openmrs.Program;
import org.openmrs.module.kenyaemr.reporting.commons.MohReportAddonCommons;
import org.openmrs.module.kenyaemr.reporting.library.moh711.Moh711CohortLibrary;
import org.openmrs.module.reporting.common.RangeComparator;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.openmrs.module.kenyacore.report.ReportUtils.map;
import static org.openmrs.module.kenyaemr.reporting.mohReportUtils.ReportingUtils.cohortIndicator;

@Component
public class Ehr711IndicatorDefinition {

	private final Moh711CohortLibrary moh711CohortDefinition;

	private final MohReportAddonCommons mohReportAddonCommons;

	@Autowired
	public Ehr711IndicatorDefinition(Moh711CohortLibrary moh711CohortDefinition, MohReportAddonCommons mohReportAddonCommons) {
		this.moh711CohortDefinition = moh711CohortDefinition;
		this.mohReportAddonCommons = mohReportAddonCommons;
	}

	public CohortIndicator getAllAncClients(Concept... entryPoints) {

		return cohortIndicator("ANC Clients",
				map(moh711CohortDefinition.getAllAncPmtctClients(entryPoints), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getAllImmunizedPatientsOnIPT(int sequence) {

		return cohortIndicator("Immunized Clients on IPT",
				map(moh711CohortDefinition.getIptVaccinesGivenToMothers(sequence), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getAllClientsWithMumericValuesComparedToAthreshold(Concept concept, int threshold,
																			  RangeComparator rangeComparator) {

		return cohortIndicator(
				"Numeric values compared to threshold",
				map(moh711CohortDefinition.getAllClientsWithHbLessThanAthreshold(concept, threshold, rangeComparator),
						"onOrAfter=${startDate},onOrBefore=${endDate}"));
	}

	public CohortIndicator getPatientWithCodedObs(Concept concept, List<Concept> answers) {

		return cohortIndicator(
				"Number of patients with coded obs over time",
				map(moh711CohortDefinition.getPatientWithCodedObs(concept, answers), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getPatientWithCodedObsAndProgram(Program program, Concept question, List<Concept> ans) {

		return cohortIndicator(
				"Number of patients with coded obs over time and program",
				map(moh711CohortDefinition.getMotherServicesProgramAndServices(program, question, ans),
						"startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getPatientGestationPeriod(int period) {

		return cohortIndicator("Number of patients with gestation period of 12 weeks",
				map(moh711CohortDefinition.getGestationPeriod(period), "endDate=${endDate}"));
	}

	public CohortIndicator getPatientWithSgbv() {

		return cohortIndicator("Number of gender based violence reported",
				map(moh711CohortDefinition.getSgbvCases(), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getPatientsHavingEncountersFilled(List<Integer> types) {
		return cohortIndicator("Number of patients having encounter over time period",
				map(mohReportAddonCommons.patientHasEncounter(types), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator getPatientsHavingEncountersAndFormsFilled(List<Integer> types, List<Integer> formTypes) {
		return cohortIndicator("Number of patients having encounter and forms filled over time period",
				map(mohReportAddonCommons.patientHasEncounterAndForms(types, formTypes), "startDate=${startDate},endDate=${endDate}"));
	}

}
