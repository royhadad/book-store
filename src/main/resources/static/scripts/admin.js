function updateBook(submitButton) {


    disableOrEnableAllButtons(false);
    const id = submitButton.id;
    const formElement = document.querySelector("#updateBooksForm");
    const formRow = formElement.querySelector(`tr#row${id}`);
    const book = getBookFromFormElement(formRow);

    if (!book.title) {
        alertMessageAndEnableButtons("invalid title!");
    } else if (!book.author) {
        alertMessageAndEnableButtons("invalid author!");
    } else if (!(book.year && book.year > 0)) {
        alertMessageAndEnableButtons("invalid year!");
    } else if (!(book.price && book.price > 0)) {
        alertMessageAndEnableButtons("invalid price!");
    } else {
        console.log('updating book with id ' + id + "...");
        console.log(book);

        fetch('http://localhost:8080/api/books/' + id, {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            method: 'put',
            body: JSON.stringify(book)
        })
            .then(function (response) {
                if (response.status !== 200) {
                    throw new Error(response.status);
                }
                alertMessageAndEnableButtons(`updated book with id ${id} successfully`);
            })
            .catch(function (err) {
                alertMessageAndEnableButtons('Something went wrong. status: ' + err.message);
            });
    }
    return false;
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
            alertMessageAndEnableButtons('Something went wrong. status: ' + err.message);
        });
}

function addBook(formElement) {
    disableOrEnableAllButtons(false);
    console.log("adding item to store...");
    console.log(formElement);

    const book = getBookFromFormElement(formElement);
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
            alertMessageAndEnableButtons('Something went wrong. status: ' + err.message);
        });
    return false;
}

function getBookFromFormElement(formElement) {
    const inputFields = formElement.querySelectorAll('input');
    const book = {};
    Array.from(inputFields).forEach((inputField) => {
        if (inputField.name === 'title' || inputField.name === 'author' || inputField.name === 'year' || inputField.name === 'price')
            book[inputField.name] = inputField.value;
    });
    book.year = parseInt(book.year);
    book.price = parseFloat(book.price);
    return book;
}

function onLoad() {
    Array.from(document.getElementsByClassName("inputYear")).forEach(inputField => {
        inputField.max = new Date().getFullYear();
    });
}
window.onload = onLoad;