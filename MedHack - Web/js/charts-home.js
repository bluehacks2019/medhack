$(document).ready(function () {

    'use strict';

    Chart.defaults.global.defaultFontColor = '#75787c';


    // ------------------------------------------------------- //
    // Line Chart
    // ------------------------------------------------------ //
    var legendState = true;
    if ($(window).outerWidth() < 576) {
        legendState = false;
    }

    var LINECHART = $('#lineCahrt');
    var myLineChart = new Chart(LINECHART, {
        type: 'line',
        options: {
            scales: {
                xAxes: [{
                    display: true,
                    gridLines: {
                        display: false
                    }
                }],
                yAxes: [{
                    ticks: {
                        max: 100,
                        min: 0
                    },
                    display: true,
                    gridLines: {
                        display: false
                    }
                }]
            },
            legend: {
                display: legendState
            }
        },
        data: {
            labels: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10"],
            datasets: [
                {
                    label: "Great",
                    fill: true,
                    lineTension: 0.2,
                    backgroundColor: "transparent",
                    borderColor: '#2ecc71',
                    pointBorderColor: '#2ecc71',
                    pointHoverBackgroundColor: '#27ae60',
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    borderWidth: 2,
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 5,
                    pointHoverRadius: 5,
                    pointHoverBorderColor: "#fff",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 0,
                    data: [20, 27, 20, 35, 30, 40, 33, 25, 39],
                    spanGaps: false
                },
                {
                    label: "Good",
                    fill: true,
                    lineTension: 0.2,
                    backgroundColor: "transparent",
                    borderColor: "#3498db",
                    pointBorderColor: '#3498db',
                    pointHoverBackgroundColor: "#2980b9",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    borderWidth: 2,
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 5,
                    pointHoverRadius: 5,
                    pointHoverBorderColor: "#fff",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: [25, 17, 28, 25, 33, 27, 30, 33, 27],
                    spanGaps: false
                },
                {
                    label: "Meh",
                    fill: true,
                    lineTension: 0.2,
                    backgroundColor: "transparent",
                    borderColor: "#f1c40f",
                    pointBorderColor: '#f1c40f',
                    pointHoverBackgroundColor: "#f39c12",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    borderWidth: 2,
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 5,
                    pointHoverRadius: 5,
                    pointHoverBorderColor: "#fff",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: [45, 27, 21, 37, 82, 29, 11, 32, 76],
                    spanGaps: false
                },
                {
                    label: "Bad",
                    fill: true,
                    lineTension: 0.2,
                    backgroundColor: "transparent",
                    borderColor: "#e67e22",
                    pointBorderColor: '#e67e22',
                    pointHoverBackgroundColor: "#d35400",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    borderWidth: 2,
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 5,
                    pointHoverRadius: 5,
                    pointHoverBorderColor: "#fff",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: [29, 12, 75, 50, 42, 2, 97,29, 28],
                    spanGaps: false
                },
                {
                    label: "Awful",
                    fill: true,
                    lineTension: 0.2,
                    backgroundColor: "transparent",
                    borderColor: "#e74c3c",
                    pointBorderColor: '#e74c3c',
                    pointHoverBackgroundColor: "#c0392b",
                    borderCapStyle: 'butt',
                    borderDash: [],
                    borderDashOffset: 0.0,
                    borderJoinStyle: 'miter',
                    borderWidth: 2,
                    pointBackgroundColor: "#fff",
                    pointBorderWidth: 5,
                    pointHoverRadius: 5,
                    pointHoverBorderColor: "#fff",
                    pointHoverBorderWidth: 2,
                    pointRadius: 1,
                    pointHitRadius: 10,
                    data: [81, 2, 9, 10, 75, 22, 91, 61, 50],
                    spanGaps: false
                }
            ]
        }
    });

});
