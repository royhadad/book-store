function addToCart(id) {
    disableOrEnableAllButtons(false);
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
            alertMessageAndEnableButtons('Something went wrong. status: ' + err.message);
        });
}
function deleteCart() {
    disableOrEnableAllButtons(false);
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
            alertMessageAndEnableButtons('Something went wrong. status: ' + err.message);
        });
}