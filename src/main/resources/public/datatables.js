var table =$('.table').DataTable({
    "columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 1, 'asc' ]]
} );

table.on( 'order.dt search.dt', function () {
    table.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
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
