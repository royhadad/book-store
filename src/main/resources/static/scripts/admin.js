function updateBook(id) {
    console.log('updating book with id ' + id + "...");

}
function deleteBook(id) {
    disableOrEnableAllButtons(false);
    console.log('deleting book with id ' + id + '...');

    fetch('http://localhost:8080/api/books/' + id, {
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
            disableOrEnableAllButtons(true);
        });
}

function addBook(formElement) {
    disableOrEnableAllButtons(false);
    console.log("adding item to store...");
    const inputFields = formElement.querySelectorAll('input');
    const book = {};
    Array.from(inputFields).forEach((inputField) => {
        if (inputField.name === 'title' || inputField.name === 'author' || inputField.name === 'year' || inputField.name === 'price')
            book[inputField.name] = inputField.value;
    });
    book.year = parseInt(book.year);
    book.price = parseFloat(book.price);
    console.log(book);

    fetch('http://localhost:8080/api/books', {
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        },
        method: 'post',
        body: JSON.stringify(book)
    })
        .then(function (response) {
            if (response.status !== 200) {
                throw new Error(response.status);
            }
            location.reload();
        })
        .catch(function (err) {
            alert('Something went wrong. status: ' + err.message);
            disableOrEnableAllButtons(true);
        });
    return false;
}

function onLoad() {
    Array.from(document.getElementsByClassName("inputYear")).forEach(inputField => {
        inputField.max = new Date().getFullYear();
    });
}
window.onload = onLoad;