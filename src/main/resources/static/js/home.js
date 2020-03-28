$(document).ready(function() {
    $.getJSON('http://localhost:8080/employee', function(json) {
        let tr=[];
        for (let i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].employeeId + '</td>');
            tr.push('<td>' + json[i].firstName + '</td>');
            tr.push('<td>' + json[i].lastName + '</td>');
            tr.push('<td>' + json[i].departmentId + '</td>');
            tr.push('<td>' + json[i].jobTitle + '</td>');
            tr.push('<td>' + json[i].gender + '</td>');
            tr.push('<td>' + json[i].dateOfBirth + '</td>');
            tr.push('<td><button class=\'edit\'>Edit</button>&nbsp;&nbsp;<button class=\'delete\' id=' + json[i].employeeId + '>Delete</button></td>');
            tr.push('</tr>');
        }
        $('table').append($(tr.join('')));
    });

    $(document).delegate('#addNew', 'click', function(event) {
        event.preventDefault();

        let firstName = $('#firstName').val();
        let lastName = $('#lastName').val();
        let departmentId = $('#departmentId').val();
        let jobTitle = $('#jobTitle').val();
        let gender = $('#gender').val();
        let dateOfBirth = $('#dateOfBirth').val();

        $.ajax({
            type: "POST",
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:8080/employee",
            data: JSON.stringify({
                'firstName': firstName,
                'lastName' : lastName,
                'departmentId' : departmentId,
                'jobTitle' : jobTitle,
                'gender' : gender,
                'dateOfBirth' : dateOfBirth
            }),
            cache: false,
            success: function(result) {
                $("#msg").html( "<span style='color: green'>Company added successfully</span>" );
                window.setTimeout(function(){location.reload()},1000)
            },
            error: function(err) {
                $("#msg").html( "<span style='color: red'>Name is required</span>" );
                window.setTimeout(function(){location.reload()},1000)
            }
        });
    });

    $(document).delegate('.delete', 'click', function() {
        if (confirm('Do you really want to delete record?')) {
            let employeeId = $(this).attr('id');
            console.log(employeeId);
            let parent = $(this).parent().parent();
            $.ajax({
                type: "DELETE",
                url: "http://localhost:8080/employee/" + employeeId,
                cache: false,
                success: function() {
                    parent.fadeOut('slow', function() {
                        $(this).remove();
                    });
                    location.reload(true)
                },
                error: function() {
                    $('#err').html('<span style=\'color:red; font-weight: bold; font-size: 30px;\'>Error deleting record').fadeIn().fadeOut(4000, function() {
                        $(this).remove();
                    });
                }
            });
        }
    });

    $(document).delegate('.edit', 'click', function() {
        let parent = $(this).parent().parent();

        let employeeId = parent.children("td:nth-child(1)");
        let firstName = parent.children("td:nth-child(2)");
        let lastName = parent.children("td:nth-child(3)");
        let departmentId = parent.children("td:nth-child(4)");
        let jobTitle = parent.children("td:nth-child(5)");
        let gender = parent.children("td:nth-child(6)");
        let dateOfBirth = parent.children("td:nth-child(7)");
        let buttons = parent.children("td:nth-child(8)");

        firstName.html("<input type='text' id='txtName' value='" + firstName.html() + "'/>");
        lastName.html("<input type='text' id='txtName' value='" + lastName.html() + "'/>");
        departmentId.html("<input type='text' id='txtName' value='" + departmentId.html() + "'/>");
        jobTitle.html("<input type='text' id='txtName' value='" + jobTitle.html() + "'/>");
        gender.html("<input type='text' id='txtName' value='" + gender.html() + "'/>");
        dateOfBirth.html("<input type='text' id='txtName' value='" + dateOfBirth.html() + "'/>");
        buttons.html("<button id='save'>Save</button>&nbsp;&nbsp;<button class='delete' id='" + employeeId.html() + "'>Delete</button>");
    });

    $(document).delegate('#save', 'click', function() {
        let parent = $(this).parent().parent();

        let employeeId = parent.children("td:nth-child(1)");
        let firstName = parent.children("td:nth-child(2)");
        let lastName = parent.children("td:nth-child(3)");
        let departmentId = parent.children("td:nth-child(4)");
        let jobTitle = parent.children("td:nth-child(5)");
        let gender = parent.children("td:nth-child(6)");
        let dateOfBirth = parent.children("td:nth-child(7)");
        let buttons = parent.children("td:nth-child(8)");

        $.ajax({
            type: "PUT",
            contentType: "application/json; charset=utf-8",
            url: "http://localhost:8080/employee/" + employeeId.html(),
            data: JSON.stringify({
                'employeeId' : employeeId.html(),
                'firstName' : firstName.children("input[type=text]").val(),
                'lastName' : lastName.children("input[type=text]").val(),
                'departmentId' : departmentId.children("input[type=text]").val(),
                'jobTitle' : jobTitle.children("input[type=text]").val(),
                'gender' : gender.children("input[type=text]").val(),
                'dateOfBirth' : dateOfBirth.children("input[type=text]").val()
            }),
            cache: false,
            success: function() {
                firstName.html(firstName.children("input[type=text]").val());
                lastName.html(lastName.children("input[type=text]").val());
                departmentId.html(departmentId.children("input[type=text]").val());
                jobTitle.html(jobTitle.children("input[type=text]").val());
                gender.html(gender.children("input[type=text]").val());
                dateOfBirth.html(dateOfBirth.children("input[type=text]").val());
                buttons.html("<button class='edit' id='" + employeeId.html() + "'>Edit</button>&nbsp;&nbsp;<button class='delete' id='" + employeeId.html() + "'>Delete</button>");
            },
            error: function() {
                $('#err').html('<span style=\'color:red; font-weight: bold; font-size: 30px;\'>Error updating record').fadeIn().fadeOut(4000, function() {
                    $(this).remove();
                });
            }
        });
    });

});