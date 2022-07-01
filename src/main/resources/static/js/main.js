$('document').ready(function() {
    $('.table #editButton').on('click', function(event) {
        event.preventDefault();

        var href=$(this).attr('href');

        $.get(href, function(currentUser){
            $('#idEdit').val(currentUser.id);
            $('#firstNameEdit').val(currentUser.firstName);
            $('#lastNameEdit').val(currentUser.lastName);
            $('#ageEdit').val(currentUser.age);
            $('#emailEdit').val(currentUser.email);
            $('#rolesEdit').val(currentUser.roles);
        });

        $('#editModal').modal();
    });

    $('.table #deleteButton').on('click', function(event) {
        event.preventDefault();
        var href=$(this).attr('href');

        $.get(href, function(currentUser){
            $('#idDelete').val(currentUser.id);
            $('#firstNameDelete').val(currentUser.firstName);
            $('#lastNameDelete').val(currentUser.lastName);
            $('#ageDelete').val(currentUser.age);
            $('#emailDelete').val(currentUser.email);
            $('#rolesDelete').val(currentUser.roles);
        });

        $('#deleteModal').modal();
    });
});