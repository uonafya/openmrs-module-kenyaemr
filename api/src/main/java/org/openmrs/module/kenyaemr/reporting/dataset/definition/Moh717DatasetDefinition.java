/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.dataset.definition;

import org.openmrs.EncounterType;
import org.openmrs.Form;
import org.openmrs.api.context.Context;
import org.openmrs.module.kenyacore.report.ReportUtils;
import org.openmrs.module.kenyaemr.reporting.indicator.Ehr711IndicatorDefinition;
import org.openmrs.module.kenyaemr.reporting.indicator.EhrMoh717IndicatorDefinition;
import org.openmrs.module.kenyaemr.reporting.library.dimesions.EhrAddonDimension;
import org.openmrs.module.kenyaemr.reporting.mohReportUtils.ColumnParameters;
import org.openmrs.module.kenyaemr.reporting.mohReportUtils.ReportAddonUtils;
import org.openmrs.module.kenyaemr.reporting.mohReportUtils.ReportingUtils;
import org.openmrs.module.kenyaemr.util.EhrAddonsConstants;
import org.openmrs.module.reporting.dataset.definition.CohortIndicatorDataSetDefinition;
import org.openmrs.module.reporting.dataset.definition.DataSetDefinition;
import org.openmrs.module.reporting.evaluation.parameter.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.openmrs.module.kenyaemr.util.EhrAddonsConstants.getConcept;
import static org.openmrs.module.reporting.evaluation.parameter.Mapped.map;
@Component
public class Moh717DatasetDefinition {

	private final EhrMoh717IndicatorDefinition ehrMoh717IndicatorDefinition;

	private final Ehr711IndicatorDefinition ehr711IndicatorDefinition;
	private final EhrAddonDimension ehrAddonDimension;
	@Autowired
	public Moh717DatasetDefinition(EhrMoh717IndicatorDefinition ehrMoh717IndicatorDefinition, Ehr711IndicatorDefinition ehr711IndicatorDefinition, EhrAddonDimension ehrAddonDimension) {
		this.ehrMoh717IndicatorDefinition = ehrMoh717IndicatorDefinition;
        this.ehr711IndicatorDefinition = ehr711IndicatorDefinition;
        this.ehrAddonDimension = ehrAddonDimension;
    }

	public DataSetDefinition constructMoh717Dataset() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		dsd.setName("MOH717");
		dsd.addParameter(new Parameter("startDate", "Start date", Date.class));
		dsd.addParameter(new Parameter("endDate", "end date", Date.class));
		String indParams = "startDate=${startDate},endDate=${endDate}";

		EncounterType opdEncounterType = Context.getEncounterService().getEncounterTypeByUuid(
		    "ba45c278-f290-11ea-9666-1b3e6e848887");

		dsd.addDimension("age", map(ehrAddonDimension.getAge(), "effectiveDate=${endDate}"));
		dsd.addDimension("gender", map(ehrAddonDimension.getGender(), ""));
		dsd.addDimension("clinic",
		    map(ehrAddonDimension.getSpecialClinicVisits(), "onOrAfter=${startDate},onOrBefore=${endDate}"));
		dsd.addDimension("state", map(ehrAddonDimension.newOrRevisits(), "startDate=${startDate},endDate=${endDate}"));

		ReportingUtils.addRow(dsd, "OSN", "OUTPATIENT SERVICES NEW PATIENTS",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getAllNewPatients(), indParams), getGeneralOutPatientFilters());

		ReportingUtils.addRow(dsd, "OSR", "OUTPATIENT SERVICES REVIST PATIENTS",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getAllRevisitPatients(), indParams), getGeneralOutPatientFilters());

		ReportingUtils.addRow(dsd, "SPCN", "SPECIAL CLINICS NEW PATIENTS",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getSpecialClinicNewPatients(), indParams),
		    getSpecialClinicPatientFilters());

		ReportingUtils.addRow(dsd, "SPCR", "SPECIAL CLINICS REVISIT PATIENTS",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getSpecialClinicRevisitPatients(), indParams),
		    getSpecialClinicPatientFilters());

		dsd.addColumn("AOSCN", "All Other special Clinic - New",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getSpecialClinicOutOfRangePatients(), indParams), "state=NEW");
		dsd.addColumn("AOSCR", "All Other special Clinic - Revisit",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getSpecialClinicOutOfRangePatients(), indParams), "state=RVT");

		dsd.addColumn("MOPCNEW", "All MOPC - NEW",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getSpecialClinicMopc(), indParams), "state=NEW");

		dsd.addColumn("MOPCRVT", "All MOPC - REVISIT",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getSpecialClinicMopc(), indParams), "state=RVT");

		dsd.addColumn(
		    "DENTALNEW",
		    "Attendances (Excluding fillings and extractions) - NEW",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getDentalVisits(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.DENTAL_OPD).getConceptId(),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.DENTAL_SPECIAL_CLINIC).getConceptId(),
		        opdEncounterType.getEncounterTypeId()), indParams), "state=NEW");

		dsd.addColumn(
		    "DENTALRVT",
		    "Attendances (Excluding fillings and extractions) - REVISIT",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getDentalVisits(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.DENTAL_OPD).getConceptId(),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.DENTAL_SPECIAL_CLINIC).getConceptId(),
		        opdEncounterType.getEncounterTypeId()), indParams), "state=RVT");
		//additional indicators on 717 report
		dsd.addColumn("DENTALFILLNEW", "Filling - NEW", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Temporary_Filling_Per_Tooth),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Amalgam_filling),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Composite_Filling),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Glass_Lonomer_Filling))), indParams), "state=NEW");
		dsd.addColumn("DENTALFILLRVT", "Filling - RVT", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Temporary_Filling_Per_Tooth),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Amalgam_filling),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Composite_Filling),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Glass_Lonomer_Filling))), indParams), "state=RVT");
		dsd.addColumn("DENTALEXTNEW", "Extraction - NEW", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Extra_Tooth_extraction),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Tooth_Extraction_simple),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Excision_of_Tooth))), indParams), "state=NEW");
		dsd.addColumn("DENTALEXTRVT", "Extraction - RVT", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Extra_Tooth_extraction),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Tooth_Extraction_simple),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Excision_of_Tooth))), indParams), "state=RVT");
		dsd.addColumn("MEDEXAM", "MEdical Examination", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.MEDICAL_EXAMINATION_ROUTINE),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Student_Medical_Assessment))), indParams), "");
		dsd.addColumn("DRESSING", "Dressing", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.CLEAN_AND_DRESSING),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Suture_wound_with_dressing),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Wound_Dressing),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Change_of_Dressing),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Burn_dressing),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Dressing_change_under_anesthesia),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Dressing_change_for_open_wound_of_breast),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Cleaning_and_Dressing),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Casualty_Dressing),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.ENT_Dressing),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Removal_Of_Ear_Dressing),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Wound_Dressing_ENT),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Dressing_Per_Session_Female_Medical_Ward))), indParams), "");
		dsd.addColumn("REMSTI", "Removal of Stitches", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Removal_Of_Stitches),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Removal_Of_Corneal_Stitches))), indParams), "");
		dsd.addColumn("INJEC", "Injection", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.INJECTION),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Sub_Conjuctiral_Sub_Tenon_Injections),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Intra_arterial_injection),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Intralesional_injection))), indParams), "");
		dsd.addColumn("STITCH", "Stitching", ReportUtils.map(ehr711IndicatorDefinition.getPatientWithCodedObs(
		    getConcept(EhrAddonsConstants._EhrAddOnConcepts.Procedure_performed), Arrays.asList(
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.STITCHING),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Stitching_Casualty),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Stitching_In_Minor_Theatre),
		        getConcept(EhrAddonsConstants._EhrAddOnConcepts.Dental_stitching))), indParams), "");

		EncounterType mchChildFollowupEncounterType = Context.getEncounterService().getEncounterTypeByUuid(
		    "bcc6da85-72f2-4291-b206-789b8186a021");
		EncounterType mchChildImmunizationEncounterType = Context.getEncounterService().getEncounterTypeByUuid(
		    "82169b8d-c945-4c41-be62-433dfd9d6c86");
		EncounterType CWC_Enrollment = Context.getEncounterService().getEncounterTypeByUuid(
		    "415f5136-ca4a-49a8-8db3-f994187c3af6");

		EncounterType consultationsEncounterType = Context.getEncounterService().getEncounterTypeByUuid(
		    "c6d09e05-1f25-4164-8860-9f32c5a02df0");
		Form pncFollowupForm = Context.getFormService().getFormByUuid("72aa78e0-ee4b-47c3-9073-26f3b9ecc4a7");
		Form ancForm = Context.getFormService().getFormByUuid("e8f98494-af35-4bb8-9fc7-c409c8fed843");

		dsd.addColumn("CWCNEW", "CWC Attendances - NEW",
		    ReportUtils.map(ehr711IndicatorDefinition.getPatientsHavingEncountersFilled(Arrays.asList(
		        mchChildFollowupEncounterType.getEncounterTypeId(), CWC_Enrollment.getEncounterTypeId(),
		        mchChildImmunizationEncounterType.getEncounterTypeId())), indParams), "state=NEW");

		dsd.addColumn("CWCRVT", "CWC Attendances - RVT",
		    ReportUtils.map(ehr711IndicatorDefinition.getPatientsHavingEncountersFilled(Arrays.asList(
		        mchChildFollowupEncounterType.getEncounterTypeId(), CWC_Enrollment.getEncounterTypeId(),
		        mchChildImmunizationEncounterType.getEncounterTypeId())), indParams), "state=RVT");

		dsd.addColumn("ANCNEW", "ANC Attendances - NEW", ReportUtils.map(
				ehr711IndicatorDefinition.getPatientsHavingEncountersAndFormsFilled(
		        Arrays.asList(consultationsEncounterType.getEncounterTypeId()), Arrays.asList(ancForm.getFormId())),
		    indParams), "state=NEW");
		dsd.addColumn("ANCRVT", "ANC Attendances - RVT", ReportUtils.map(
				ehr711IndicatorDefinition.getPatientsHavingEncountersAndFormsFilled(
		        Arrays.asList(consultationsEncounterType.getEncounterTypeId()), Arrays.asList(ancForm.getFormId())),
		    indParams), "state=RVT");

		dsd.addColumn(
		    "PNCNEW",
		    "PNC Attendances - NEW",
		    ReportUtils.map(
					ehr711IndicatorDefinition.getPatientsHavingEncountersAndFormsFilled(
		            Arrays.asList(consultationsEncounterType.getEncounterTypeId()),
		            Arrays.asList(pncFollowupForm.getFormId())), indParams), "state=NEW");
		dsd.addColumn(
		    "PNCRVT",
		    "PNC Attendances - RVT",
		    ReportUtils.map(
					ehr711IndicatorDefinition.getPatientsHavingEncountersAndFormsFilled(
		            Arrays.asList(consultationsEncounterType.getEncounterTypeId()),
		            Arrays.asList(pncFollowupForm.getFormId())), indParams), "state=RVT");

		return dsd;
	}

	private List<ColumnParameters> getGeneralOutPatientFilters() {

		ColumnParameters under5Male = new ColumnParameters("under5Mal", "below 5 male", "age=<5|gender=M", "01");
		ColumnParameters under5Female = new ColumnParameters("under5Female", "below 5 female", "age=<5|gender=F", "02");
		ColumnParameters over5Male = new ColumnParameters("over5Male", "above 5 male", "age=>5|gender=M", "03");
		ColumnParameters over5Female = new ColumnParameters("over5Female", "above 5 female", "age=>5|gender=F", "04");

		ColumnParameters over60 = new ColumnParameters("over60", "above 60 years", "age=>60", "05");

		return Arrays.asList(under5Male, under5Female, over5Male, over5Female, over60);

	}

	private List<ColumnParameters> getSpecialClinicPatientFilters() {
		ColumnParameters entClinic = new ColumnParameters("ENT", "ENT Clinic", "clinic=ENT", "01");
		ColumnParameters eyeClinic = new ColumnParameters("EYE", "Eye Clinic", "clinic=EYE", "02");

		ColumnParameters tbClinic = new ColumnParameters("tbAndLeprosy", "TB and Leprosy Clinic", "clinic=TBL", "03");

		ColumnParameters stiClinic = new ColumnParameters("STI", "STI Clinic", "clinic=STI", "04");

		ColumnParameters cccClinic = new ColumnParameters("CCC", "CCC Clinic", "clinic=CCC", "05");

		ColumnParameters psychiatryClinic = new ColumnParameters("psychiatryClinic", "Psychiatry Clinic", "clinic=PSY", "06");

		ColumnParameters orthopaedicClinic = new ColumnParameters("orthopaedicClinic", "Orthopaedic Clinic", "clinic=ORT",
		        "07");

		ColumnParameters occupationalTherapyClinic = new ColumnParameters("occupationalTherapyClinic",
		        "Occupational Therapy Clinic", "clinic=OCP", "08");

		ColumnParameters physiotherapyClinic = new ColumnParameters("physiotherapyClinic", "Physiotherapy Clinic",
		        "clinic=PHYS", "09");

		ColumnParameters surgicalClinic = new ColumnParameters("surgicalClinics", "Surgical Clinics", "clinic=SC", "11");

		ColumnParameters paediatricsClinic = new ColumnParameters("paediatricsClinic", "Paediatrics Clinic", "clinic=PAED",
		        "12");

		ColumnParameters obstetricsGynaecologyClinic = new ColumnParameters("obstetricsGynaecologyClinic",
		        "Obstetrics/Gynaecology clinic", "clinic=OG", "13");

		ColumnParameters nutritionClinic = new ColumnParameters("nutritionClinic", "Nutrition clinic", "clinic=NUC", "14");

		ColumnParameters oncologyClinic = new ColumnParameters("oncologyClinic", "Oncology clinic", "clinic=ONC", "15");

		ColumnParameters renalClinic = new ColumnParameters("renalClinic", "Renal clinic", "clinic=RENAL", "16");

		return Arrays.asList(entClinic, eyeClinic, tbClinic, stiClinic, cccClinic, psychiatryClinic, orthopaedicClinic,
		    occupationalTherapyClinic, physiotherapyClinic, surgicalClinic, paediatricsClinic, obstetricsGynaecologyClinic,
		    nutritionClinic, oncologyClinic, renalClinic);
	}

	public DataSetDefinition constructRevisitAndNewPatients() {
		CohortIndicatorDataSetDefinition dsd = new CohortIndicatorDataSetDefinition();
		String indParam = "startDate=${startDate},endDate=${endDate}";
		dsd.setName("MOH717AB");
		dsd.addParameter(new Parameter("startDate", "Start Date", Date.class));
		dsd.addParameter(new Parameter("endDate", "End Date", Date.class));
		dsd.addDimension("day", ReportUtils.map(ehrAddonDimension.encountersOfMonthPerDay(), indParam));
		dsd.addDimension("gender", map(ehrAddonDimension.getGender(), ""));

		ReportingUtils.addRow(dsd, "NFAC", "No of first attendances",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getNewChildrenPatients(), indParam),
		    ReportAddonUtils.getAdultChildrenWithGenderColumns());

		ReportingUtils.addRow(dsd, "RAC", "Re-attendances",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getRevisitsChildrenPatients(), indParam),
				ReportAddonUtils.getAdultChildrenWithGenderColumns());

		ReportingUtils.addRow(dsd, "NFAA", "No. Of First Attendances",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getNewAdultsPatients(), indParam),
				ReportAddonUtils.getAdultChildrenWithGenderColumns());

		ReportingUtils.addRow(dsd, "RETA", "Re-Attendances",
		    ReportUtils.map(ehrMoh717IndicatorDefinition.getRevisitsAdultsPatients(), indParam),
				ReportAddonUtils.getAdultChildrenWithGenderColumns());

		return dsd;
	}
}
