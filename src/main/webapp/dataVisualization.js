google.charts.load('current', {packages: ['corechart']});

google.charts.setOnLoadCallback(drawChart);

function drawChart(productData) {
    var data = google.visualization.arrayToDataTable([
        ['Product Name', 'Items Available'],
        ...productData 
    ]);

    var options = {
        title: 'Product Inventory',
        hAxis: {
            title: 'Product Name',
            minValue: 0
        },
        vAxis: {
            title: 'Items Available',
        },
        legend: { position: 'none' }
    };

    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}

function drawSalesChart(salesData) {
    var data = new google.visualization.DataTable();
    data.addColumn('string', 'Product Name');
    data.addColumn('number', 'Total Sales');

    data.addRows(salesData);

    var options = {
        title: 'Total Sales by Product',
        chartArea: { width: '75%' },
        hAxis: {
            title: 'Total Sales',
            minValue: 0
        },
        vAxis: {
            title: 'Product Name'
        },
        height: 600 
    };

    var chart = new google.visualization.BarChart(document.getElementById('chart_div'));
    chart.draw(data, options);
}

function toggleVisibility() {
    const showTable = document.getElementById('showTable').checked;
    const showChart = document.getElementById('showChart').checked;

    if (showTable) {
        document.getElementById('tableContainer').style.display = 'block';
    } else {
        document.getElementById('tableContainer').style.display = 'none';
    }

    if (showChart) {
        document.getElementById('chart_div').style.display = 'block';
    } else {
        document.getElementById('chart_div').style.display = 'none';
    }
}