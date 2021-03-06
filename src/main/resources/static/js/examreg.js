$(document).ready(function() {
    $("#startdate").datetimepicker({
        format:'d-m-Y H:i',
    	minDate:'0',
    	dayOfWeekStart:1
    });
    $("#enddate").datetimepicker({
        format:'d-m-Y H:i',
    	minDate:'0',
    	dayOfWeekStart:1
    });
    $.ajax({url: "/getClassificators", success: function(result){
        for (i = 0; i < result.length; i++) {
            var opt = document.createElement('option');
            opt.value = result[i].id;
            opt.innerHTML = result[i].classif_name;
            $('#classifSelect').append(opt);
        }
    }});
    refresh();
    $('#examreg').click(function(){
        var cookie = JSON.parse($.cookie('CSRF'));
        var map = getExamData();
        $.ajax({
            data: JSON.stringify(map),
            headers: {'X-CSRF-TOKEN': cookie.csrf},
            contentType: 'application/json',
            timeout: 5000,
            type: 'POST',
            url: '/addExamination',
            success: function(data){
                $.notify("Exam has been added successfully.", "success");
                location.reload();
            },
            error: function(errorThrown){
               console.log(errorThrown)
               $.notify("Error while trying to create new examination.", "error");

            }
        })
    });
    $('#examopen').click(function(){
        var cookie = JSON.parse($.cookie('CSRF'));
        var examid = {'id': $('#examSelect').val()}
        $.ajax({
            data: JSON.stringify(examid),
            headers: {'X-CSRF-TOKEN': cookie.csrf},
            contentType: 'application/json',
            timeout: 5000,
            type: 'POST',
            url: '/startExamination',
            success: function(data){
                $.notify("Exam has been started.", "success");
            },
            error: function(errorThrown){
                console.log(errorThrown)
                $.notify("Couldn't start the exam.", "error");
            }
        })
    });
    $('#examclose').click(function(){
        var cookie = JSON.parse($.cookie('CSRF'));
        var examid = {'id': $('#examSelect').val()}
        $.ajax({
            data: JSON.stringify(examid),
            headers: {'X-CSRF-TOKEN': cookie.csrf},
            contentType: 'application/json',
            timeout: 5000,
            type: 'POST',
            url: '/closeExamination',
            success: function(data){
                $.notify("Exam has been closed.", "success");
            },
            error: function(errorThrown){
                console.log(errorThrown);
                $.notify("Couldn't close the exam.", "error");
            }
        })
    });
    $('#examdelete').click(function(){
        var cookie = JSON.parse($.cookie('CSRF'));
        var examid = {'id': $('#examSelect').val()}
        $.ajax({
            data: JSON.stringify(examid),
            headers: {'X-CSRF-TOKEN': cookie.csrf},
            contentType: 'application/json',
            timeout: 5000,
            type: 'POST',
            url: '/deleteExamination',
            success: function(data){
                location.reload();
            },
            error: function(errorThrown){
                console.log(errorThrown)
                $.notify("Couldn't deactivate the exam.", "error");
            }
        })
    });
    $('#headers').load('/html/components/header.html');
});
function getExamData(){
    return {'startdate': $('#startdate').val(),'enddate': $('#enddate').val(),'classif':$('#classifSelect').val()}
}
function refresh(){
    var select = document.getElementById("examSelect");
    var length = select.options.length;
    for (i = 0; i < length; i++) {
      select.options[i] = null;
    }
    var opt = document.createElement('option');
    opt.innerHTML = "Created examinations.."
    $('#examSelect').append(opt);
    $.ajax({url: "/getExaminations", success: function(result){
        for (i = 0; i < result.length; i++) {
                var m = result[i]
                if(m['is_deactivated']=='false'){
                    var opt = document.createElement('option');
                    opt.value = m['id'];
                    var d1 = m['startdate'].slice(0,-5);
                    var d2 = m['enddate'].slice(0,-5);
                    opt.innerHTML = d1 + " to " + d2 + "; Question catergory: " + m['classif_name'];
                    $('#examSelect').append(opt);
                }
        }
    }});
}