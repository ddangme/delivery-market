$(document).ready(function() {
    $('#amount').on('input', function() {
        let inputVal = $(this).val().replace(/[^0-9]/g, '');

        if (parseInt(inputVal, 10) > 1000000) {
            inputVal = '1000000';
        }
        $(this).val(inputVal);
    });

    $('#submit').click(function () {
        const amount = $('#amount').val();
        const form = $('#form');
        if (!confirm(amount.toLocaleString() + " 원을 충전하시겠습니까?")) {
            return;
        }

        $.ajax({
            url: form.attr('action'),
            type: form.attr('method'),
            data: { amount: amount },
            success: function (response) {
                alert("충전 요청을 하였습니다.");
                $('#amount').val('');
            },
            error: function (xhr) {
                alert("충전에 실패하였습니다.\n" + xhr.responseText);
            },
        });
    });
});