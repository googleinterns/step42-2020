/** Loads photo */
function loadScripts() {
  fetchBlobstoreUrl();
  populatePosts();
}


/** Loads upload image URL and attaches it to the form */
function fetchBlobstoreUrl() {
  fetch('/blobstore-upload-url') 
      .then((response) => {
        return response.text();
      })
      .then((imageUploadUrl) => {
        const messageForm = document.getElementById('plant-pic-form');
        messageForm.action = imageUploadUrl;
      });
}


/**
  * Creates and adds a photo 
  * pic is the URL of image to be shown in the post
*/
function createListElement(pic) {
  let card = document.createElement("div");
  card.className = "card m-1";

  let body = document.createElement("div");
  body.className = "card-body";

  let photo = document.createElement("img");
  photo.style.width = "100%";
  photo.src = pic;

  body.append(photo);

  card.append(body);

  document.getElementById('posts').append(card);
}

/**
  * Populates the list element with JSON fetched from the /data servlet.
*/
function populatePosts() {
  fetch('/data').then(response => response.json()).then((blobKey) => {
    const postHolder = document.getElementById('posts');
    postHolder.innerHTML = "";
    fetch('/get-image?blobKey=' + blobKey).then((pic) => {
      createListElement(pic.url);
    });
  });
}
