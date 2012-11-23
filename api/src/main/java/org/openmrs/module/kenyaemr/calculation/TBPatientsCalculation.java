/*
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.calculation;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.openmrs.Program;
import org.openmrs.api.context.Context;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.SimpleResult;
import org.openmrs.module.kenyaemr.MetadataConstants;

public class TBPatientsCalculation extends BaseKenyaEmrCalculation {

    @Override
    public String getShortMessage() {
        return "TB Patients";
    }

    @Override
    public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> arg1, PatientCalculationContext ctx) {
        Program tbProgram = Context.getProgramWorkflowService().getProgramByUuid(MetadataConstants.TB_PROGRAM_UUID);
        Set<Integer> inTbProgram = CalculationUtils.patientsThatPass(lastProgramEnrollment(tbProgram, cohort, ctx));
        Set<Integer> alive = alivePatients(cohort, ctx);

        CalculationResultMap ret = new CalculationResultMap();
        for (Integer ptId : cohort) {
            boolean inProgram = false;
            if (inTbProgram.contains(ptId) && alive.contains(ptId)) {
                inProgram = true;
            }
            ret.put(ptId, new SimpleResult(inProgram, this, ctx));
        }

        return ret;
    }


}
