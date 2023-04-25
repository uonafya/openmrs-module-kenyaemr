package org.openmrs.module.kenyaemr.reporting.cohort.definition.evaluator.sgbv;

/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.module.kenyaemr.reporting.cohort.definition.sgbv.SGBVLineListCohortDefinition;
import org.openmrs.module.reporting.cohort.EvaluatedCohort;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.evaluator.CohortDefinitionEvaluator;
import org.openmrs.module.reporting.evaluation.EvaluationContext;
import org.openmrs.module.reporting.evaluation.EvaluationException;
import org.openmrs.module.reporting.evaluation.querybuilder.SqlQueryBuilder;
import org.openmrs.module.reporting.evaluation.service.EvaluationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Evaluator for SGBVLineListCohortDefinition
 */
@Handler(supports = {SGBVLineListCohortDefinition.class})
public class SGBVLinelistCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

    private final Log log = LogFactory.getLog(this.getClass());

    @Autowired
    EvaluationService evaluationService;

    @Override
    public EvaluatedCohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) throws EvaluationException {

        SGBVLineListCohortDefinition definition = (SGBVLineListCohortDefinition) cohortDefinition;

        if (definition == null)
            return null;

        Cohort newCohort = new Cohort();

        String qry="SELECT patient_id FROM kenyaemr_etl.etl_gbv_screening\n" +
                " where visit_date between date(:startDate) and date(:endDate) and location_id in (:defaultLocation)\n" +
                "GROUP BY patient_id;";

        SqlQueryBuilder builder = new SqlQueryBuilder();
        builder.append(qry);
        Date startDate = (Date)context.getParameterValue("startDate");
        Date endDate = (Date)context.getParameterValue("endDate");
        builder.addParameter("startDate", startDate);
        builder.addParameter("endDate", endDate);
        builder.addParameter("defaultLocation", context.getParameterValue("defaultLocation"));

        List<Integer> ptIds = evaluationService.evaluateToList(builder, Integer.class, context);
        newCohort.setMemberIds(new HashSet<Integer>(ptIds));
        return new EvaluatedCohort(newCohort, definition, context);
    }
}
