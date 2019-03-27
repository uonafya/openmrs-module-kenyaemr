<%
    ui.includeCss("kenyaemr", "referenceapplication.css", 100)
%>
<style>
.alignLeft {
    text-align: left;
}
</style>
<script>
    jQuery(function () {
        jQuery('#care_and_treatment_chart').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Number of Patients'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                name: 'Statistics',
                colorByPoint: true,
                data: [{

                    name: 'Total Patients',
                    y:${allPatients},

                }, {
                    name: 'Total enrolled in HIV',
                    y: ${cumulativeEnrolledInHiv},

                }, {
                    name: 'Current in Care',
                    y: ${inCare},

                }, {
                    name: 'Current on ART',
                    y: ${onArt},

                }, {
                    name: 'Newly Enrolled',
                    y: ${newlyEnrolledInHiv},

                }, {
                    name: 'New on ART',
                    y: ${newOnArt},
                }]
            }],
        });
    });

    jQuery(function () {
        jQuery('#viral_load_tracker').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Number of Patients'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                name: 'Statistics',
                colorByPoint: true,
                data: [{

                    name: 'Total clients with viral loads',
                    y:${vlResults},

                }, {
                    name: 'Total Unsuppressed',
                    y: ${vlResults - suppressedVl},

                }, {
                    name: 'Total Suppressed',
                    y: ${suppressedVl},

                }]
            }],
        });
    });

    jQuery(function () {
        jQuery('#hts_tracker').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Number of Patients'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                name: 'Statistics',
                colorByPoint: true,
                data: [{

                    name: 'Total Tested',
                    y:${htsTested},

                }, {
                    name: 'Total Positive',
                    y: ${htsPositive},

                }, {
                    name: 'Total Linked',
                    y: ${htsLinked},

                }]
            }],
        });
    });

    jQuery(function () {
        jQuery('#hts_contacts_tracker').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Number of Patient Contacts'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                name: 'Statistics',
                colorByPoint: true,
                data: [{

                    name: 'Family contacts tested',
                    y:${htsTestedFamily},

                }, {
                    name: 'HIV+ family contacts',
                    y: ${htsPositiveFamily},

                }, {
                    name: 'Family contacts unknown status',
                    y: ${htsUnknownStatusFamily},

                }, {
                    name: 'Family contacts linked',
                    y: ${htsLinkedFamily},
                },{
                    name: 'Partners tested',
                    y:${htsTestedPartners},

                }, {
                    name: 'HIV+ partners',
                    y: ${htsPositivePartner},

                }, {
                    name: 'Partners Unknown status',
                    y: ${htsUnknownStatusPartner},

                }, {
                    name: 'Partners linked',
                    y: ${htsLinkedPartner},
                },{
                    name: 'IDU tested',
                    y:${htsTestedIDU},

                }, {
                    name: 'IDU HIV+',
                    y: ${htsPositiveIDU},

                }, {
                    name: 'IDU Unknown status',
                    y: ${htsUnknownStatusIDU},

                }, {
                    name: 'IDU linked',
                    y: ${htsLinkedIDU}

                }]
            }],
        });
    });

    jQuery(function () {
        jQuery('#differentiated_care_tracker').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Current on Treatment'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                name: 'Statistics',
                colorByPoint: true,
                data: [
                    {
                        name: 'Total current on treatment',
                        y: ${currInCareOnART},

                    },
                    {
                        name: 'Total Stable patients',
                        y: ${stableOver4mtca+stableUnder4mtca},

                    },
                    {

                        name: 'Stable patients with under 4 months prescription',
                        y:${stableUnder4mtca},

                    }, {
                        name: 'Stable patients with 4+ months prescription',
                        y: ${stableOver4mtca},

                    },

                ]
            }],
        });
    });

    jQuery(function () {
        jQuery('#differentiated_care_tracker_stable').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Stable patients'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                name: 'Statistics',
                colorByPoint: true,
                data: [

                    {
                        name: '<15 years below 4 months prescription',
                        y: ${stableUnder4mtcaBelow15},

                    },

                    {
                        name: '15+ years Females below 4 months prescription',
                        y: ${stableUnder4mtcaOver15F},

                    },
                    {
                        name: '15+ years Males below 4 months prescription',
                        y: ${stableUnder4mtcaOver15M},

                    },
                    {
                        name: '<15 years 4+ months prescription',
                        y: ${stableOver4mtcaBelow15},

                    },
                    {
                        name: '15+ years Females 4+ months prescription',
                        y: ${stableOver4mtcaOver15F},

                    },
                    {
                        name: '15+ years Males 4+ months prescription',
                        y: ${stableOver4mtcaOver15M},

                    },
                ]
            }],
        });
    });


    jQuery(function () {
        jQuery('#differentiated_care_tracker_unstable').highcharts({
            chart: {
                type: 'column'
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                type: 'category'
            },
            yAxis: {
                title: {
                    text: 'Unstable patients'
                }

            },
            legend: {
                enabled: false
            },
            plotOptions: {
                series: {
                    borderWidth: 0,
                    dataLabels: {
                        enabled: true,
                        format: '{point.y:.0f}'
                    }
                }
            },

            tooltip: {
                headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
                pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.0f}</b><br/>'
            },

            series: [{
                name: 'Statistics',
                colorByPoint: true,
                data: [

                    {
                        name: '<15 years',
                        y: ${unstableUnder15},

                    },

                    {
                        name: '15+ years Females',
                        y: ${unstableFemales15Plus},

                    },
                    {
                        name: '15+ years males',
                        y: ${unstableMales15Plus},

                    },

                ]
            }],
        });
    });
</script>

<div class="ke-page-content">
    <div style="font-size: 18px; color: #006056; font-style: normal; font-weight: bold">Facility Dashboard</div>

    <div id="program-tabs" class="ke-tabs">
        <div class="ke-tabmenu">
            <div class="ke-tabmenu-item" data-tabid="care_and_treatment">Care and Treatment</div>

            <div class="ke-tabmenu-item" data-tabid="hts">HTS</div>

            <div class="ke-tabmenu-item" data-tabid="appointments">Appointments</div>

            <div class="ke-tabmenu-item" data-tabid="diff_care">Differentiated Care</div>


        </div>

        <div class="ke-tab" data-tabid="care_and_treatment">
            <table cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td style="width: 50%; vertical-align: top">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">Summary of Care and Treatment Statistics</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="3"
                                            class="heading2"><strong>Reporting Period: ${reportPeriod}</strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Total Patients</th>
                                        <th>Total enrolled in HIV</th>
                                        <th>Current in Care</th>
                                        <th>Current on ART</th>
                                        <th>Newly Enrolled</th>
                                        <th>New on ART</th>
                                    </tr>
                                    <tr>
                                        <td>${allPatients}</td>
                                        <td>${cumulativeEnrolledInHiv}</td>
                                        <td>${inCare}</td>
                                        <td>${onArt}</td>
                                        <td>${newlyEnrolledInHiv}</td>
                                        <td>${newOnArt}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <div id="care_and_treatment_chart" style="min-width: 450px; height: 300px; margin: 0 auto"></div>
                    </td>
                    <td style="width: 50%; vertical-align: top; padding-left: 5px">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">Viral Load Tracker</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="3" class="heading2"><strong>Reporting Period: Today</strong></td>
                                    </tr>
                                    <tr>
                                        <th>Total clients with viral loads <br/>(in last 12 months)</th>
                                        <th>Total Unsuppressed</th>
                                        <th>Total Suppressed</th>
                                    </tr>
                                    <tr>
                                        <td>${vlResults}</td>
                                        <td>${vlResults - suppressedVl}</td>
                                        <td>${suppressedVl}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                        <div id="viral_load_tracker" style="min-width: 450px; height: 300px; margin: 0 auto"></div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="ke-tab" data-tabid="hts">
            <table cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td style="width: 50%; vertical-align: top">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">HTS</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="3" class="heading2"><strong>Reporting Period: Today</strong></td>
                                    </tr>
                                    <tr>
                                        <th>&nbsp;</th>
                                        <th>Total Tested</th>
                                        <th>Total Positive</th>
                                        <th>Total Enrolled</th>
                                    </tr>
                                    <tr>
                                        <td><b>Total Clients</b></td>
                                        <td>${htsTested}</td>
                                        <td>${htsPositive}</td>
                                        <td>${htsLinked}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div id="hts_tracker" style="min-width: 450px; height: 300px; margin: 0 auto"></div>
                    </td>
                    <td style="width: 50%; vertical-align: top; padding-left: 5px">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">Contact Testing</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="3" class="heading2"><strong>Reporting Period: Today</strong></td>
                                    </tr>
                                    <tr>
                                        <th>&nbsp;</th>
                                        <th>Total Tested</th>
                                        <th>Total Positive</th>
                                        <th>Total Unknown</th>
                                        <th>Total Enrolled</th>
                                    </tr>

                                    <tr>
                                        <td><b>Family Member(s)</b></td>
                                        <td>${htsTestedFamily}</td>
                                        <td>${htsPositiveFamily}</td>
                                        <td>${htsUnknownStatusFamily}</td>
                                        <td>${htsLinkedFamily}</td>
                                    </tr>
                                    <tr>
                                        <td><b>Sexual Partner(s)</b></td>
                                        <td>${htsTestedPartners}</td>
                                        <td>${htsPositivePartner}</td>
                                        <td>${htsUnknownStatusPartner}</td>
                                        <td>${htsLinkedPartner}</td>
                                    </tr>
                                    <tr>
                                        <td><b>Injectable Drug User(s)</b></td>
                                        <td>${htsTestedIDU}</td>
                                        <td>${htsPositiveIDU}</td>
                                        <td>${htsUnknownStatusIDU}</td>
                                        <td>${htsLinkedIDU}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div id="hts_contacts_tracker" style="min-width: 700px; height: 350px; margin: 0 auto"></div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="ke-tab" data-tabid="appointments">
            <table cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td style="width: 50%; vertical-align: top">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">Today's Workload</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="3"
                                            class="heading2"><strong>Date: Today</strong>
                                        </td>
                                    </tr>
                                    <tr>
                                        <th>Number Scheduled </th>
                                        <th>Number Checked In </th>
                                        <th>Number Seen</th>
                                        <th>Unscheduled Visits</th>

                                    </tr>
                                    <tr>
                                        <td>${patientsScheduled}</td>
                                        <td>${checkedIn}</td>
                                        <td>${patientsSeen}</td>
                                        <td>${unscheduled}</td>
                                    </tr>
                                </table>
                            </div>
                        </div>

                    </td>

                </tr>
            </table>
        </div>
        <div class="ke-tab" data-tabid="diff_care">
            <table cellspacing="0" cellpadding="0" width="100%">
                <tr>
                    <td style="width: 50%; vertical-align: top; padding-left: 5px">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">Unstable Patients</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="4" class="heading2"><strong>Reporting Period: Today</strong></td>
                                    </tr>

                                    <tr>
                                        <th>Aged Below 15 years</th>
                                        <th>Females aged 15+ years</th>
                                        <th>Males aged 15+ years</th>
                                    </tr>
                                    <tr>
                                        <td>${unstableUnder15}</td>
                                        <td>${unstableFemales15Plus}</td>
                                        <td>${unstableMales15Plus}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="3" class="heading"><strong>Total Unstable: ${unstableUnder15 + unstableFemales15Plus + unstableMales15Plus }</strong></td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <div id="differentiated_care_tracker_unstable" style="min-width: 900px; height: 350px; margin: 0 auto"></div>
                    </td>

                    <td style="width: 50%; vertical-align: top; padding-left: 5px">
                        <div class="ke-panel-frame">
                            <div class="ke-panel-heading">Stable Patients</div>

                            <div class="ke-panel-content">
                                <table class="alignLeft">
                                    <tr>
                                        <td colspan="6" class="heading2"><strong>Reporting Period: Today</strong></td>
                                    </tr>

                                    <tr>
                                        <th colspan="3">Under 4 months prescription</th>
                                        <th colspan="3">4+ months prescription</th>
                                    </tr>
                                    <tr>
                                        <th colspan="1">Below 15 years</th>
                                        <th colspan="1">Females 15+ years</th>
                                        <th colspan="1">Males 15+ years</th>
                                        <th colspan="1">Below 15 years</th>
                                        <th colspan="1">Females 15+ years</th>
                                        <th colspan="1">Males 15+ years</th>
                                    </tr>
                                    <tr>
                                        <td>${stableUnder4mtcaBelow15}</td>
                                        <td>${stableUnder4mtcaOver15F}</td>
                                        <td>${stableUnder4mtcaOver15M}</td>
                                        <td>${stableOver4mtcaBelow15}</td>
                                        <td>${stableOver4mtcaOver15F}</td>
                                        <td>${stableOver4mtcaOver15M}</td>
                                    </tr>
                                    <tr>
                                        <td colspan="6" class="heading"><strong>Total Stable: ${stableUnder4mtcaBelow15 + stableUnder4mtcaOver15F + stableUnder4mtcaOver15M + stableOver4mtcaBelow15 +  stableOver4mtcaOver15F + stableOver4mtcaOver15M}</strong></td>
                                    </tr>

                                </table>
                            </div>
                        </div>
                        <div id="differentiated_care_tracker_stable" style="min-width: 900px; height: 350px; margin: 0 auto"></div>
                    </td>
                </tr>
            </table>
        </div>
        <br/>
       <br/>
    </div>
   </div>
