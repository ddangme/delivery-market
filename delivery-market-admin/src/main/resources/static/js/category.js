function addParentCategory() {
    if (!confirm("카테고리를 추가하시겠습니까?")) {
        return;
    }

    let nameInput = document.getElementById('name');
    let name = nameInput.value;

    let url = '/api/categories/new';
    let xhr = new XMLHttpRequest();
    let data = {
        name: name
    };

    xhr.open('POST', url, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                location.reload();
            } else {
                alert(xhr.responseText);
            }
        }
    };

    xhr.send(JSON.stringify(data));
}
