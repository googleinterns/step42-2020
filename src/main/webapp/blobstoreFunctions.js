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
        document.getElementById('plant-pic-form').action = imageUploadUrl;
      });
}


/**
  * Creates and adds a photo 
  * pic is the URL of image to be shown in the post
*/
function createListElement(pic) {
  let card = document.createElement("div");
  let body = document.createElement("div");
  let photo = document.createElement("img");

  card.className = "card m-1";
  body.className = "card-body";
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
