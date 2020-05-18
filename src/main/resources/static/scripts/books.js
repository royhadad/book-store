function disableOrEnableAllAddToCartButtons(enable) {
    Array.from(document.getElementsByClassName("button")).forEach(button => {
        button.onclick = enable ? () => addToCart(button.id) : undefined;
        button.style.cursor = enable ? "pointer" : "default";
    });
}
function addToCart(id) {
    disableOrEnableAllAddToCartButtons(false);
    console.log("adding item with id " + id + " to cart...");

    fetch('http://localhost:8080/api/shopping-cart', {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        method: 'post',
        body: id
    })
        .then(function (response) {
            if (response.status !== 201) {
                throw new Error(response.status);
            }
            location.reload();
        })
        .catch(function (err) {
            alert('Something went wrong. status: ' + err.message);
            disableOrEnableAllAddToCartButtons(true);
        });
}
function deleteCart() {
    disableOrEnableAllAddToCartButtons(false);
    console.log("deleting cart...");

    fetch('http://localhost:8080/api/shopping-cart', {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        method: 'delete'
    })
        .then(function (response) {
            if (response.status !== 200) {
                throw new Error(response.status);
            }
            location.reload();
        })
        .catch(function (err) {
            alert('Something went wrong. status: ' + err.message);
            disableOrEnableAllAddToCartButtons(true);
        });
}