/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 *
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.kenyaemr.reporting.queries;

import java.util.List;

public class Moh705Queries {

	/**
	 * Get patients who conform to the diagnosis based on given concepts
	 *
	 * @return String of children
	 */
	public static String getPatientsWhoMatchDiagnosisBasedOnConcepts(int provisional, int finalDiagnosis, int encounter,
	        List<Integer> listOptions) {
		String str1 = String.valueOf(listOptions).replaceAll("\\[", "");
		String list = str1.replaceAll("]", "");
		String query = "SELECT pat.patient_id FROM patient pat " + " INNER JOIN encounter e ON pat.patient_id=e.patient_id "
		        + " INNER JOIN obs ob ON e.encounter_id=ob.encounter_id " + " WHERE "
		        + " e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND ob.concept_id IN(%d, %d) "
		        + " AND e.encounter_type IN(%d) " + " AND ob.value_coded IS NOT NULL " + " AND ob.value_coded IN(%s)";
		return String.format(query, provisional, finalDiagnosis, encounter, list);
	}

	public static String getPatientsWhoMatchOtherDiagnosisBasedOnConcepts(int provisional, int finalDiagnosis,
	        int encounter, List<Integer> listOptions) {
		String str1 = String.valueOf(listOptions).replaceAll("\\[", "");
		String list = str1.replaceAll("]", "");
		String query = "SELECT pat.patient_id FROM patient pat " + " INNER JOIN encounter e ON pat.patient_id=e.patient_id "
		        + " INNER JOIN obs ob ON e.encounter_id=ob.encounter_id " + " WHERE "
		        + " e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND ob.concept_id IN(%d, %d) "
		        + " AND e.encounter_type IN(%d) " + " AND ob.value_coded IS NOT NULL " + " AND ob.value_coded NOT IN(%s)";
		return String.format(query, provisional, finalDiagnosis, encounter, list);
	}

	public static String getPatientsWhoAreReferred(int question, int ans) {
		String query = "SELECT pat.patient_id FROM patient pat " + " INNER JOIN encounter e ON pat.patient_id=e.patient_id "
		        + " INNER JOIN obs ob ON e.encounter_id=ob.encounter_id " + " WHERE "
		        + " e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND ob.concept_id=%d "
		        + " AND ob.value_coded IS NOT NULL " + " AND ob.value_coded=%d";
		return String.format(query, question, ans);
	}

	/**
	 * put you first indicator query here MOH 705a
	 *
	 * @return String
	 */
	public static String getMoh705aQuery(int classId, int encounter) {
		String sql = "SELECT "
		        + " cn.name AS Diagnosis, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 1 THEN 1 ELSE 0 END) AS 1st, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 2 THEN 1 ELSE 0 END) AS 2nd, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 3 THEN 1 ELSE 0 END) AS 3rd, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 4 THEN 1 ELSE 0 END) AS 4th, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 5 THEN 1 ELSE 0 END) AS 5th, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 6 THEN 1 ELSE 0 END) AS 6th, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 7 THEN 1 ELSE 0 END) AS 7th,"
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 8 THEN 1 ELSE 0 END) AS 8th,"
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 9 THEN 1 ELSE 0 END) AS 9th, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 10 THEN 1 ELSE 0 END) AS 10th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 11 THEN 1 ELSE 0 END) AS 11th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 12 THEN 1 ELSE 0 END) AS 12th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 13 THEN 1 ELSE 0 END) AS 13th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 14 THEN 1 ELSE 0 END) AS 14th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 15 THEN 1 ELSE 0 END) AS 15th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 16 THEN 1 ELSE 0 END) AS 16th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 17 THEN 1 ELSE 0 END) AS 17th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 18 THEN 1 ELSE 0 END) AS 18th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 19 THEN 1 ELSE 0 END) AS 19th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 20 THEN 1 ELSE 0 END) AS 20th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 21 THEN 1 ELSE 0 END) AS 21st, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 22 THEN 1 ELSE 0 END) AS 22nd, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 23 THEN 1 ELSE 0 END) AS 23rd, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 24 THEN 1 ELSE 0 END) AS 24th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 25 THEN 1 ELSE 0 END) AS 25th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 26 THEN 1 ELSE 0 END) AS 26th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 27 THEN 1 ELSE 0 END) AS 27th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 28 THEN 1 ELSE 0 END) AS 28th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 29 THEN 1 ELSE 0 END) AS 29th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 30 THEN 1 ELSE 0 END) AS 30th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 31 THEN 1 ELSE 0 END) AS 31st "
		        + " FROM obs o "
		        + " INNER JOIN encounter e ON o.encounter_id = e.encounter_id "
		        + " INNER JOIN person pe ON e.patient_id=pe.person_id "
		        + " INNER JOIN person_name pn ON pn.person_id=pe.person_id "
		        + " INNER JOIN patient pa ON pa.patient_id=pe.person_id "
		        + "INNER JOIN concept_name cn ON cn.concept_id = o.value_coded AND locale = 'en' AND cn.locale_preferred = 1 "
		        + "INNER JOIN concept c ON c.concept_id=cn.concept_id "
		        + "WHERE "
		        + " e.encounter_datetime BETWEEN :startDate AND DATE_ADD(DATE_ADD(:endDate, INTERVAL 23 HOUR), INTERVAL 59 MINUTE) "
		        + " AND o.value_coded IS NOT NULL " + " AND o.concept_id IN(160249, 160250)" + " AND c.class_id IN(%d) "
		        + " AND e.encounter_type IN(%d) " + " AND TIMESTAMPDIFF(YEAR, pe.birthdate, :endDate) < 5 "
		        + "GROUP BY cn.name";

		return String.format(sql, classId, encounter);
	}

	/**
	 * put you first indicator query here MOH 705b
	 *
	 * @return String
	 */
	public static String getMoh705bQuery(int classId, int encounter) {
		String sql = "SELECT "
		        + " cn.name AS Diagnosis, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 1 THEN 1 ELSE 0 END) AS 1st, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 2 THEN 1 ELSE 0 END) AS 2nd, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 3 THEN 1 ELSE 0 END) AS 3rd, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 4 THEN 1 ELSE 0 END) AS 4th, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 5 THEN 1 ELSE 0 END) AS 5th, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 6 THEN 1 ELSE 0 END) AS 6th, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 7 THEN 1 ELSE 0 END) AS 7th,"
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 8 THEN 1 ELSE 0 END) AS 8th,"
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 9 THEN 1 ELSE 0 END) AS 9th, "
		        + " SUM(CASE DAY(e.encounter_datetime) WHEN 10 THEN 1 ELSE 0 END) AS 10th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 11 THEN 1 ELSE 0 END) AS 11th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 12 THEN 1 ELSE 0 END) AS 12th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 13 THEN 1 ELSE 0 END) AS 13th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 14 THEN 1 ELSE 0 END) AS 14th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 15 THEN 1 ELSE 0 END) AS 15th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 16 THEN 1 ELSE 0 END) AS 16th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 17 THEN 1 ELSE 0 END) AS 17th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 18 THEN 1 ELSE 0 END) AS 18th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 19 THEN 1 ELSE 0 END) AS 19th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 20 THEN 1 ELSE 0 END) AS 20th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 21 THEN 1 ELSE 0 END) AS 21st, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 22 THEN 1 ELSE 0 END) AS 22nd, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 23 THEN 1 ELSE 0 END) AS 23rd, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 24 THEN 1 ELSE 0 END) AS 24th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 25 THEN 1 ELSE 0 END) AS 25th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 26 THEN 1 ELSE 0 END) AS 26th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 27 THEN 1 ELSE 0 END) AS 27th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 28 THEN 1 ELSE 0 END) AS 28th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 29 THEN 1 ELSE 0 END) AS 29th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 30 THEN 1 ELSE 0 END) AS 30th, "
		        + "    SUM(CASE DAY(e.encounter_datetime) WHEN 31 THEN 1 ELSE 0 END) AS 31st "
		        + " FROM obs o "
		        + " INNER JOIN encounter e ON o.encounter_id = e.encounter_id "
		        + " INNER JOIN person pe ON e.patient_id=pe.person_id "
		        + " INNER JOIN person_name pn ON pn.person_id=pe.person_id "
		        + " INNER JOIN patient pa ON pa.patient_id=pe.person_id "
		        + "INNER JOIN concept_name cn ON cn.concept_id = o.value_coded AND locale = 'en' AND cn.locale_preferred = 1 "
		        + "INNER JOIN concept c ON c.concept_id=cn.concept_id "
		        + "WHERE "
		        + " e.encounter_datetime BETWEEN :startDate AND DATE_ADD(DATE_ADD(:endDate, INTERVAL 23 HOUR), INTERVAL 59 MINUTE) "
		        + " AND o.value_coded IS NOT NULL " + " AND o.concept_id IN(160249, 160250)" + " AND c.class_id IN(%d) "
		        + " AND e.encounter_type IN(%d) " + " AND TIMESTAMPDIFF(YEAR, pe.birthdate, :endDate) >= 5 "
		        + "GROUP BY cn.name";

		return String.format(sql, classId, encounter);
	}

	/**
	 * Get patients who conform to the diagnosis based on given concepts
	 *
	 * @return String of children
	 */
	public static String getPatientsWhoMatchAtLeastDiagnosisBasedOnConcepts(int provisional, int finalDiagnosis,
	        int encounter) {
		String query = "SELECT pat.patient_id FROM patient pat " + " INNER JOIN encounter e ON pat.patient_id=e.patient_id "
		        + " INNER JOIN obs ob ON e.encounter_id=ob.encounter_id " + " WHERE "
		        + " e.encounter_datetime BETWEEN :startDate AND :endDate " + " AND ob.concept_id IN(%d, %d) "
		        + " AND e.encounter_type IN(%d) " + " AND ob.value_coded IS NOT NULL ";
		return String.format(query, provisional, finalDiagnosis, encounter);
	}

}
