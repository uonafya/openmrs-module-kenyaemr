/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.library.ETLReports.MOH705;

import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.reporting.indicator.CohortIndicator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.openmrs.module.kenyaemr.reporting.EmrReportingUtils.cohortIndicator;

/**
 * Library of MOH705A indicator definitions.
 */
@Component
public class MOH705IndicatorLibrary {

	@Autowired
	private MOH705CohortLibrary moh705CohortLibrary;

	/**
	 * Diagnosis
	 */
	public CohortIndicator diagnosis(Integer diagnosis, String age) {
		return cohortIndicator("Diagnosis", ReportUtils.map(moh705CohortLibrary.diagnosis(diagnosis,age), "startDate=${startDate},endDate=${endDate}"));
	}
	/**
	 * Other Diagnosis under five
	 */
	public CohortIndicator allOtherDiseasesUnderFive(String age) {
		return cohortIndicator("Other Under Five Diagnosis", ReportUtils.map(moh705CohortLibrary.allOtherDiseasesUnderFive(age), "startDate=${startDate},endDate=${endDate}"));
	}
	/**
	 * Other Diagnosis Over five
	 */
	public CohortIndicator allOtherDiseasesAboveFive(String age) {
		return cohortIndicator("Other Above Five Diagnosis", ReportUtils.map(moh705CohortLibrary.allOtherDiseasesUnderFive(age), "startDate=${startDate},endDate=${endDate}"));
	}

	public CohortIndicator newAttendances(String age) {
		return cohortIndicator("New attendances", ReportUtils.map(moh705CohortLibrary.newAttendances(age), "startDate=${startDate},endDate=${endDate}"));
	}
	public CohortIndicator reAttendances(String age) {
		return cohortIndicator("Re attendances", ReportUtils.map(moh705CohortLibrary.reAttendances(age), "startDate=${startDate},endDate=${endDate}"));
	}
}




























