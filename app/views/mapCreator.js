var map;
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
        center: {lat: 50.068000, lng: 19.912410},
        zoom: 8
    });

    map.data.setStyle(function (feature) {
        return {
            title: feature.getProperty('name'),
            optimized: false
        };
    });

    setStationPoints();
}

function setStationPoints() {
    var table = document.getElementById('tempStationsTable');
    var rows = table.rows;
    for (var r = 0, n = rows.length; r < n; r++) {
        var name = String(rows[r].cells[1].innerHTML);
        var lan = parseFloat(String(rows[r].cells[2].innerHTML));
        var lng = parseFloat(String(rows[r].cells[3].innerHTML));
        addCity(name,lan,lng);
    }
    table.parentNode.removeChild(table);
}

function addCity(name, latitude, longitude) {
    var marker = new google.maps.Marker({
        position: {lat: latitude, lng: longitude},
        label: ' ',
        map: map
    });
    marker.addListener('click', function () {
        alert(name)
    });
}
