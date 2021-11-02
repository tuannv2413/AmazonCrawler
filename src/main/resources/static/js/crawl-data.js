function handleClick() {
    let url = escapeHtml($("#url").val());
    if (url.length > 0) {
        // $("#url").css("border","1px solid #ced4da");
        $("#d-error").css("display", "none");
        $.ajax({
            type: 'POST',
            url: "/api/crawler",
            contentType: 'application/json; charset=utf-8',
            dataType: 'json',
            data: JSON.stringify([
                url
            ]),
            success: (function (result) {
                Swal.fire({
                    position: 'mid',
                    icon: 'success',
                    title: '保存が完了しました。',
                    showConfirmButton: false,
                    timer: 1500
                });
            }),
            error: (function (error) {
                console.log(error)
            })
        })
    }else {
        // $("#url").css("border","1px solid red");
        $("#d-error").css("display", "block");
    }

}

function escapeHtml(unsafe) {
    return unsafe
        .replace(/&/g, "&amp;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#039;");
}