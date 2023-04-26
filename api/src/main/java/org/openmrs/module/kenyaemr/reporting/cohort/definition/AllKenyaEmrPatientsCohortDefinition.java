/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.cohort.definition;

import org.openmrs.Location;
import org.openmrs.module.reporting.cohort.definition.BaseCohortDefinition;
import org.openmrs.module.reporting.common.Localized;
import org.openmrs.module.reporting.definition.configuration.ConfigurationProperty;
import org.openmrs.module.reporting.definition.configuration.ConfigurationPropertyCachingStrategy;
import org.openmrs.module.reporting.evaluation.caching.Caching;

import java.util.Date;

@Caching(strategy = ConfigurationPropertyCachingStrategy.class)
@Localized("kenyaemr.AllKenyaEmrPatientsCohortDefinition")
public class AllKenyaEmrPatientsCohortDefinition extends BaseCohortDefinition {

    public static final long serialVersionUID = 1L;

    public AllKenyaEmrPatientsCohortDefinition() {
    }

    public String toString() {
        return "All KenyaEMR Patients";
    }

    public Integer getDefaultLocation() {
        return defaultLocation;
    }

    public void setDefaultLocation(Integer defaultLocation) {
        this.defaultLocation = defaultLocation;
    }

    @ConfigurationProperty private Integer defaultLocation;

}
