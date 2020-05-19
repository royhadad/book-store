function disableOrEnableAllButtons(enable) {
    Array.from(document.getElementsByClassName("button")).forEach(button => {
        button.onclick = enable ? () => addToCart(button.id) : undefined;
        button.style.cursor = enable ? "pointer" : "default";
    });
}