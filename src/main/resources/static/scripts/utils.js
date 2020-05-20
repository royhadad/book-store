function disableOrEnableAllButtons(enable) {
    Array.from(document.getElementsByClassName("button")).forEach(button => {
        button.disabled = enable ? false : true;
        button.style.cursor = enable ? "pointer" : "default";
    });
    Array.from(document.getElementsByClassName("tableFormField")).forEach(input => {
        input.disabled = enable ? false : true;
    });
}

function alertMessageAndEnableButtons(msg) {
    disableOrEnableAllButtons(true);
    alert(msg);
}