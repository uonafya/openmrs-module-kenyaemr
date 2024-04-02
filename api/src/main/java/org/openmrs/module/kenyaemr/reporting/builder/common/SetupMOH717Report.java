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

import org.openmrs.EncounterType;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.report.ReportDescriptor;
import org.openmrs.module.kenyacore.report.builder.AbstractReportBuilder;
import org.openmrs.module.kenyacore.report.builder.Builds;
import org.openmrs.module.kenyaemr.reporting.dataset.definition.Moh717DatasetDefinition;
import org.openmrs.module.kenyaemr.reporting.library.moh717.Moh717CohortDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Mapped;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.openmrs.module.reporting.report.definition.ReportDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.reporting.evaluation.parameter.Mapped.map;

@Component
@Builds({ "kenyaemr.common.report.moh717" })
public class SetupMOH717Report extends AbstractReportBuilder {

	private final Moh717DatasetDefinition moh717DatasetDefinition;

	private final Moh717CohortDefinition moh717CohortDefinition;

	@Autowired
	public SetupMOH717Report(Moh717DatasetDefinition moh717DatasetDefinition, Moh717CohortDefinition moh717CohortDefinition) {
		this.moh717DatasetDefinition = moh717DatasetDefinition;
		this.moh717CohortDefinition = moh717CohortDefinition;
	}

	@Override
	protected List<Parameter> getParameters(ReportDescriptor reportDescriptor) {
		return Arrays.asList(new Parameter("startDate", "Start Date", Date.class), new Parameter("endDate", "End Date",
		        Date.class), new Parameter("dateBasedReporting", "", String.class));
	}

	@Override
	protected List<Mapped<DataSetDefinition>> buildDataSets(ReportDescriptor reportDescriptor,
	        ReportDefinition reportDefinition) {
		EncounterType opdEncounterType = Context.getEncounterService().getEncounterTypeByUuid(
		    "ba45c278-f290-11ea-9666-1b3e6e848887");
		reportDefinition.setBaseCohortDefinition(map(
		    moh717CohortDefinition.getAllPatientsWithDiagnosis(opdEncounterType.getEncounterTypeId()),
		    "startDate=${startDate},endDate=${endDate+23h}"));
		return Arrays.asList(
		    map(moh717DatasetDefinition.constructMoh717Dataset(), "startDate=${startDate},endDate=${endDate+23h}"),
		    map(moh717DatasetDefinition.constructRevisitAndNewPatients(), "startDate=${startDate},endDate=${endDate+23h}"));
	}

}
