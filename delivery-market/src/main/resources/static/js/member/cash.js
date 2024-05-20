$(document).ready(function() {
    getChargingList();
    validateAmount();
    submit();
});

const trArea = $(`
<tr>
    <td class="amount"></td>
    <td class="status"></td>
    <td class="requestAt"></td>
    <td class="responseAt"></td>
</tr>
`);

function getChargingList() {
    $.get(
        "/api/my-page/cash/list",
        function (response) {
            response.forEach(function (item) {
                let tr = trArea.clone();
                tr.attr('id', item.id);
                tr.find('.amount').text(item.amount.toLocaleString() + " 원");
                tr.find('.status').text(item.status);
                changeFontColor(item.status, tr.find('.status'));
                tr.find('.requestAt').text(parseDate(item.requestAt));
                if (item.responseAt === null) {
                    tr.find('.responseAt').text("-");
                } else {
                    tr.find('.responseAt').text(parseDate(item.responseAt));
                }
                $('#cash-list').append(tr);
            });
        }
    );
}

function changeFontColor(response, $td) {
    if (response === "요청") {
        $td.addClass('')
    } else if (response === "승낙") {
        $td.addClass('text-success');
    } else if (response === "거절") {
        $td.addClass('text-danger')
    } else if (response === "보류") {
        $td.addClass('text-warning');
    }
}

function parseDate(request) {
    const date = new Date(request);

    return date.getFullYear() + '년 ' +
        (date.getMonth() + 1) + '월 ' +
        date.getDate() + '일 ' +
        date.getHours().toString().padStart(2, '0') + ':' +
        date.getMinutes().toString().padStart(2, '0') + ':' +
        date.getSeconds().toString().padStart(2, '0');
}

function validateAmount() {
    $('#amount').on('input', function() {
        let inputVal = $(this).val().replace(/[^0-9]/g, '');

        if (parseInt(inputVal, 10) > 1000000) {
            inputVal = '1000000';
        }
        $(this).val(inputVal);
    });
}

function submit() {
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
                location.reload();
            },
            error: function (xhr) {
                alert("충전 요청에 실패하였습니다.\n" + xhr.responseText);
            },
        });
    });
}