var tableqb = $('#qb-table').DataTable({
    "columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );

tableqb.on( 'order.dt search.dt', function () {
    tableqb.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();

var tablerb = $('#rb-table').DataTable({
    "columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );

tablerb.on( 'order.dt search.dt', function () {
    tablerb.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();

var tablewr = $('#wr-table').DataTable({
    "columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );

tablewr.on( 'order.dt search.dt', function () {
    tablewr.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();

var tablete = $('#te-table').DataTable({
    "columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );

tablete.on( 'order.dt search.dt', function () {
    tablete.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();

var tablek = $('#k-table').DataTable({
    "columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );

tablek.on( 'order.dt search.dt', function () {
    tablek.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();

var tablede = $('#de-table').DataTable({
    "columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );

tablede.on( 'order.dt search.dt', function () {
    tablede.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();

$('#qb-table-box').show();

$('#qb').click(function() {
  $('.table-box').hide();
  $('#qb-table-box').show();
});
$('#rb').click(function() {
  $('.table-box').hide();
  $('#rb-table-box').show();
});
$('#wr').click(function() {
  $('.table-box').hide();
  $('#wr-table-box').show();
});
$('#te').click(function() {
  $('.table-box').hide();
  $('#te-table-box').show();
});
$('#k').click(function() {
  $('.table-box').hide();
  $('#k-table-box').show();
});
$('#de').click(function() {
  $('.table-box').hide();
  $('#de-table-box').show();
});
