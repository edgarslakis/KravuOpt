$(document).ready(function () {
    $.getJSON("/vehicles/list", function(vehicles) {
        var listofvehicles = $("#listofvehicles");
        $.each(vehicles, function(idx, value) {
            listofvehicles.append($('<li><a href="vehicle.html?id='+ value.solutionId + '">' +
                value.score +'</a><a href="vehicle_leaflet.html?id='+ value.solutionId + '"> (map) </a>' +
                ' packs: ' + value.packList.length + '</li>'));
        });
    });
});