const trArea = $(`
<tr>
    <td class="id"></td>
    <td class="amount"></td>
    <td class="status"></td>
    <td class="memberName"></td>
    <td class="adminName"></td>
    <td class="requestAt"></td>
    <td class="responseAt"></td>
</tr>
`)

$(document).ready(function () {
    getCashChargingList();
});

function getCashChargingList() {
    $.ajax({
        url: "/api/members/cash/list",
        type: "GET",
        success: function (response) {
            console.log(response);
            response.content.forEach(function (data) {
                let tr = trArea.clone();
                tr.find('.id').text(data.id);
                tr.find('.amount').text(data.amount);
                tr.find('.status').text(data.status);
                changeFontColor(data.status, tr.find('.status'));
                tr.find('.memberName').text(data.memberName);
                tr.find('.adminName').text(data.adminName);
                tr.find('.requestAt').text(parseDate(data.requestAt));
                if (data.responseAt === null) {
                    tr.find('.responseAt').text("-");
                } else {
                    tr.find('.responseAt').text(parseDate(data.responseAt));
                }

                $('#cash-charging-list').append(tr);
            })
        },
        error: function (xhr) {
            alert(xhr.responseText);
            window.history;
        },
    })
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