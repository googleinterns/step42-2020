/** Loads action URL and attaches it to the upload form (the user's browser uploads the file to blobstore with the generated url) */
function fetchBlobstoreUrl() {
  fetch('/blobstore-upload-url').then((response) => response.text()).then((imageUploadUrl) => {
    document.getElementById('plant-pic-form').action = imageUploadUrl;
  });
}
 
/**
  * Creates and adds a card element for the photo 
  * pic_url is the URL of image to be shown in the post
*/
function createCardElement(pic_url) {
  let card = document.createElement("div");
  let card_body = document.createElement("div");
  let photo = document.createElement("img");
  photo.style.width = "100px";
 
  card.className = "card m-1";
  card_body.className = "card-body";
  photo.src = pic_url;
 
  card_body.append(photo);
  card.append(card_body);
  document.getElementById('uploads').append(card);
}
 
/**
  * Populates the list element with JSON fetched from the /get-blob-key servlet.
*/
function imageToPage() {
  fetch('/get-blob-key').then(response => response.json()).then((blobKey) => {
    if(blobKey != "noKey"){
      fetch('/get-image?blobKey=' + blobKey).then((pic) => {
        createCardElement(pic.url);
      });
    }
  });
}
