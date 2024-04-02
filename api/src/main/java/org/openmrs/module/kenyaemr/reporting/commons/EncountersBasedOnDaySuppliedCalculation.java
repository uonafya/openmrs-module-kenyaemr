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

import org.openmrs.Encounter;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Calculations;
import org.openmrs.module.kenyaemr.calculation.EmrCalculationUtils;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

    public class EncountersBasedOnDaySuppliedCalculation extends AbstractPatientCalculation {

        @Override
        public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
                PatientCalculationContext context) {
            CalculationResultMap resultMap = new CalculationResultMap();
            Integer day = (Integer) parameterValues.get("day");

            CalculationResultMap encounter = Calculations.lastEncounter(null, cohort, context);
            for (Integer pId : cohort) {
                boolean found = false;
                Encounter patientEncounter = EmrCalculationUtils.encounterResultForPatient(encounter, pId);
                if (patientEncounter != null && patientEncounter.getEncounterDatetime() != null && day != null) {
                    if (formatDate(patientEncounter.getEncounterDatetime()).equals(
                        formatDate(getDateBasedOnValue(context.getNow(), day)))) {
                        found = true;
                    }

                }

                resultMap.put(pId, new BooleanResult(found, this));
            }
            return resultMap;
        }

        private Date getDateBasedOnValue(Date date, int day) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(year, month, day);

            return calendar1.getTime();
        }

        private String formatDate(Date date) {

            Format formatter = new SimpleDateFormat("yyyy-MM-dd");

            return formatter.format(date);
        }
    }
